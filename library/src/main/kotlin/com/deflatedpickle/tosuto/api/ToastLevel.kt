/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.api

import java.awt.Color
import javax.swing.Icon
import javax.swing.UIManager

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
    DEBUG(Color.CYAN, UIManager.getIcon("OptionPane.questionIcon")),
    INFO(Color.GREEN, UIManager.getIcon("OptionPane.informationIcon")),
    WARNING(Color.ORANGE, UIManager.getIcon("OptionPane.warningIcon")),
    ERROR(Color.RED, UIManager.getIcon("OptionPane.errorIcon"))
}
