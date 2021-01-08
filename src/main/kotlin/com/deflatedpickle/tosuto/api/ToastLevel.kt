/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.api

import java.awt.Color
import javax.swing.Icon
import javax.swing.JOptionPane
import javax.swing.UIManager
import sun.swing.DefaultLookup

val optionPane = JOptionPane()

/**
 * Levels of severity, with colours, to use for [ToastItem]s
 *
 * Colours are based on Log4J loggers
 */
@Suppress("unused")
enum class ToastLevel(
    val colour: Color,
    val icon: Icon
) {
    // The icon requests had to be changed due to a bug on Linux
    DEBUG(Color.CYAN, DefaultLookup.getIcon(optionPane, optionPane.ui, "OptionPane.questionIcon")),
    INFO(Color.GREEN, DefaultLookup.getIcon(optionPane, optionPane.ui, "OptionPane.informationIcon")),
    WARNING(Color.ORANGE, DefaultLookup.getIcon(optionPane, optionPane.ui, "OptionPane.warningIcon")),
    ERROR(Color.RED, DefaultLookup.getIcon(optionPane, optionPane.ui, "OptionPane.errorIcon"))
}
