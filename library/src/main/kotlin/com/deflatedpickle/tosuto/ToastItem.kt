package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.api.ToastCommand
import com.deflatedpickle.tosuto.command.ToastMultiCommand
import com.deflatedpickle.tosuto.command.ToastSingleCommand
import com.deflatedpickle.tosuto.constraints.FillHorizontal
import com.deflatedpickle.tosuto.constraints.FillHorizontalFinishLine
import com.deflatedpickle.tosuto.constraints.FillVerticalStickEast
import com.deflatedpickle.tosuto.constraints.FinishLine
import java.awt.Cursor
import java.awt.GridBagLayout
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

/**
 * An item to be placed in a [ToastWindow]
 */
// TODO: Add actions for the user to take
class ToastItem(
    level: ToastLevel = ToastLevel.INFO,
    icon: Icon = transparentIcon,
    title: String = "",
    content: String = """""",
    buttons: Set<ToastButtonType> = setOf(ToastButtonType.CLOSE),
    actions: Set<ToastCommand> = setOf()
) : JPanel() {
    companion object {
        val transparentIcon = ImageIcon()
    }

    private val iconLabel = JLabel(icon)
    private val titleLabel = JLabel(title).apply {
        this.font = this.font.deriveFont(12f)
    }

    // A box for action buttons to live in
    private val titleBarButtons = JPanel().apply {
        this.layout = BoxLayout(this, BoxLayout.X_AXIS)

        for (i in buttons) {
            when (i) {
                // TODO: Replace the text with icons
                ToastButtonType.CLOSE -> this.add(JButton("X").apply {
                    addActionListener {
                        with(this@ToastItem.parent) {
                            remove(this@ToastItem)
                            revalidate()
                            repaint()
                        }
                    }
                })
            }
        }
    }

    private val actionButtons = JPanel().apply {
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
                    val label = this
                    addMouseListener(object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            i.menu.show(label, e.x, e.y)
                        }
                    })
                }
                else -> JLabel("What you did doesn't work")
            }.apply {
                foreground = UIManager.getColor("List.selectionBackground")
                cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
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