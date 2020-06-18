package com.deflatedpickle.tosuto

import java.awt.Color

/**
 * Levels of severity, with colours, to use for [ToastItem]s
 *
 * Colours are based on Log4J loggers
 */
@Suppress("unused")
enum class ToastLevel(
    val colour: Color
) {
    DEBUG(Color.CYAN),
    INFO(Color.GREEN),
    WARNING(Color.ORANGE),
    ERROR(Color.RED)
}