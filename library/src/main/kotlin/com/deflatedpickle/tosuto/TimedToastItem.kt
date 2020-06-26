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
import java.lang.NullPointerException
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.JSeparator
import javax.swing.Timer
import javax.swing.UIManager
import javax.swing.border.EmptyBorder

/**
 * A toast that'll close itself after its limit
 */
class TimedToastItem @JvmOverloads constructor(
    level: ToastLevel = ToastLevel.INFO,
    title: String = "",
    content: String = """""",
    buttons: List<ToastButtonType> = listOf(ToastButtonType.CLOSE),
    actions: List<ToastAction> = listOf(),
    actionSpacing: Int = 8,
    /**
     * The amount of time in seconds before this toast disappears
     */
    limit: Int = 5000,
    /**
     * The rate this toast will progress in milliseconds
     */
    step: Int = 100
) : ToastItem(level, title, content, buttons, actions, actionSpacing) {
    private val timeProgress = JProgressBar(JProgressBar.HORIZONTAL, 0, limit)

    init {
        add(this.timeProgress, FillHorizontalFinishLine)

        Timer(step) {
            timeProgress.value += step

            if (timeProgress.value >= limit) {
                // This gets called twice, for whatever reason
                // So we'll try it first and silently fail the second
                try {
                    with(this.parent) {
                        remove(this@TimedToastItem)
                        revalidate()
                        repaint()
                    }
                } catch (e: NullPointerException) {
                }
            }
        }.start()
    }
}
