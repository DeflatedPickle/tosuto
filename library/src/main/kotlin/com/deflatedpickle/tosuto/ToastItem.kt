package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.constraints.FillHorizontal
import com.deflatedpickle.tosuto.constraints.FillHorizontalFinishLine
import com.deflatedpickle.tosuto.constraints.FillVerticalStickEast
import com.deflatedpickle.tosuto.constraints.FinishLine
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.border.EmptyBorder

/**
 * An item to be placed in a [ToastWindow]
 */
// TODO: Add actions for the user to take
class ToastItem(
    level: ToastLevel = ToastLevel.INFO,
    icon: Icon = transparentIcon,
    title: String = "",
    content: String = """""",
    buttons: Set<ToastButton> = setOf(ToastButton.CLOSE)
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
                ToastButton.CLOSE -> this.add(JButton("X").apply {
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
        })
    }
}