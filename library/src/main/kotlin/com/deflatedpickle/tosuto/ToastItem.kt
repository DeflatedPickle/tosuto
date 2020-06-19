/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.api.ToastButtonType
import com.deflatedpickle.tosuto.api.ToastCommand
import com.deflatedpickle.tosuto.api.ToastLevel
import com.deflatedpickle.tosuto.command.ToastMultiCommand
import com.deflatedpickle.tosuto.command.ToastSingleCommand
import com.deflatedpickle.tosuto.constraints.FillHorizontal
import com.deflatedpickle.tosuto.constraints.FillHorizontalFinishLine
import com.deflatedpickle.tosuto.constraints.FillVerticalStickEast
import com.deflatedpickle.tosuto.constraints.FinishLine
import java.awt.Cursor
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.GridLayout
import java.awt.Image
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.font.TextAttribute
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.SwingConstants
import javax.swing.UIManager
import javax.swing.border.EmptyBorder

/**
 * An item to be placed in a [ToastWindow]
 */
class ToastItem(
    level: ToastLevel = ToastLevel.INFO,
    title: String = "",
    content: String = """""",
    buttons: Set<ToastButtonType> = setOf(ToastButtonType.CLOSE),
    actions: Set<ToastCommand> = setOf()
) : JPanel() {
    private val titleLabel = JLabel(title).apply {
        this.font = this.font.deriveFont(12f)
    }

    private val iconLabel = JLabel(
        ImageIcon(
            (level.icon as ImageIcon).image.getScaledInstance(
                titleLabel.font.size,
                titleLabel.font.size,
                Image.SCALE_SMOOTH
            )
        )
    ).apply { border = EmptyBorder(4, 4, 4, 8) }

    // A box for action buttons to live in
    private val titleBarButtons = JPanel().apply {
        this.border = EmptyBorder(0, 4, 0, 0)
        this.layout = BoxLayout(this, BoxLayout.X_AXIS)

        for (i in buttons) {
            when (i) {
                ToastButtonType.CLOSE -> this.add(JButton(UIManager.getIcon("InternalFrame.closeIcon")).apply {
                    this.isOpaque = false
                    this.isContentAreaFilled = false
                    this.isBorderPainted = false

                    this.border = EmptyBorder(4, 8, 4, 8)

                    this.minimumSize = Dimension(32, 20)
                    this.maximumSize = Dimension(32, 20)

                    addActionListener {
                        with(this@ToastItem.parent) {
                            remove(this@ToastItem)
                            revalidate()
                            repaint()
                        }
                    }
                })
            }.apply {
                isFocusable = false
            }
        }
    }

    private val actionButtons = JPanel().apply {
        // border = BorderFactory.createLineBorder(Color.RED)
        this.layout = GridLayout(1, actions.size, 2, 2)

        for (i in actions) {
            this.add(when (i) {
                is ToastSingleCommand -> JLabel(i.text).apply {
                    addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            i.command()
                        }
                    })
                }
                is ToastMultiCommand -> JLabel(i.text).apply {
                    icon = UIManager.getIcon("Table.descendingSortIcon")
                    horizontalTextPosition = SwingConstants.LEFT

                    val label = this
                    addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            i.menu.show(
                                label,
                                e.component.x - e.component.width,
                                e.component.y + e.component.height
                            )
                        }
                    })
                }
                else -> JLabel("What you did doesn't work")
            }.apply {
                val originalFont = this.font

                val attributes: MutableMap<*, *> = this.font.attributes
                (attributes as MutableMap<TextAttribute, Any>)[TextAttribute.UNDERLINE] = TextAttribute.UNDERLINE_ON
                val underlineFont = this.font.deriveFont(attributes)

                foreground = UIManager.getColor("List.selectionBackground")
                cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

                addMouseListener(object : MouseAdapter() {
                    override fun mouseEntered(e: MouseEvent) {
                        e.component.font = underlineFont
                    }

                    override fun mouseExited(e: MouseEvent) {
                        e.component.font = originalFont
                    }
                })
            })
        }
    }

    private val contentLabel = JLabel("<html>$content</html>")

    init {
        add(JPanel().also {
            this.layout = GridBagLayout()

            this.border = BorderFactory.createEtchedBorder()

            this.add(this.iconLabel)
            this.add(this.titleLabel, FillHorizontal)
            this.add(JSeparator(JSeparator.VERTICAL), FillVerticalStickEast)
            this.add(this.titleBarButtons, FinishLine)
            this.add(JSeparator(JSeparator.HORIZONTAL).apply {
                this.background = level.colour.darker()
                this.foreground = level.colour
            }, FillHorizontalFinishLine)
            this.add(this.contentLabel, FillHorizontalFinishLine)
            this.add(this.actionButtons, FillHorizontalFinishLine)
        })
    }
}
