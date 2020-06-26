/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.action.ToastAction
import com.deflatedpickle.tosuto.api.ToastButtonType
import com.deflatedpickle.tosuto.api.ToastLevel
import com.deflatedpickle.tosuto.constraints.FillBothFinishLine
import com.deflatedpickle.tosuto.constraints.FillHorizontal
import com.deflatedpickle.tosuto.constraints.FillHorizontalFinishLine
import com.deflatedpickle.tosuto.constraints.FillVerticalStickEast
import com.deflatedpickle.tosuto.constraints.FinishLine
import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.Image
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.UIManager
import javax.swing.border.EmptyBorder

/**
 * An item to be placed in a [ToastWindow]
 */
open class ToastItem @JvmOverloads constructor(
    /**
     * Defines the colour and icon this toast will use
     *
     * @see [ToastLevel]
     */
    level: ToastLevel = ToastLevel.INFO,
    /**
     * The text used next to the icon
     */
    title: String = "",
    /**
     * The content under the coloured divider
     *
     * This is turned into a HTML string
     */
    content: String = """""",
    /**
     * The buttons used after the title
     *
     * @see [ToastButtonType]
     */
    buttons: List<ToastButtonType> = listOf(ToastButtonType.CLOSE),
    /**
     * The actions this toast will have at the bottom
     *
     * @see [ToastAction]
     */
    actions: List<ToastAction> = listOf(),
    /**
     * The spacing between each action
     */
    actionSpacing: Int = 8
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
        this.layout = BoxLayout(this, BoxLayout.X_AXIS)

        for (i in actions) {
            this.add(i)

            if (actionSpacing > 0) {
                this.add(Box.createHorizontalStrut(actionSpacing))
            }
        }
    }

    private val contentLabel = JLabel("<html>$content</html>")

    init {
        this.layout = GridBagLayout()

        this.border = BorderFactory.createEtchedBorder()

        this.add(this.iconLabel)
        this.add(this.titleLabel, FillHorizontal)
        this.add(JSeparator(JSeparator.VERTICAL), FillVerticalStickEast)
        this.add(this.titleBarButtons, FinishLine)
        this.add(JSeparator(JSeparator.HORIZONTAL).apply {
            this.background = level.colour.darker()
            this.foreground = level.colour
        }, FillBothFinishLine)
        this.add(this.contentLabel, FillBothFinishLine)
        this.add(this.actionButtons, FillHorizontalFinishLine)
    }
}
