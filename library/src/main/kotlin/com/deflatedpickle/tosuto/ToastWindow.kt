package com.deflatedpickle.tosuto

import java.awt.Color
import java.awt.Dimension
import java.awt.Frame
import java.awt.Point
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JDialog
import javax.swing.JFrame

/**
 * A [JDialog] to attach to a [JFrame] and hold [ToastItem]s
 * This will overlay it's parent by becoming top-level, until the parent looses focus, then it will stop being top-level.
 */
// TODO: There should be an anchor to hook this to
class ToastWindow(
    @Suppress("MemberVisibilityCanBePrivate")
    val parent: Frame,
    val toastWidth: Int = 140,
    val toastHeight: Int = 60
) : JDialog(parent) {
    init {
        this.isUndecorated = true
        this.background = Color(0, 0, 0, 0)

        this.contentPane.layout = ToastLayout(toastHeight = this.toastHeight)

        object : WindowAdapter() {
            override fun windowIconified(e: WindowEvent) {
                this@ToastWindow.isVisible = false
            }

            override fun windowDeiconified(e: WindowEvent) {
                this@ToastWindow.isVisible = true
            }

            override fun windowGainedFocus(e: WindowEvent) {
                if (e.newState and WindowEvent.WINDOW_GAINED_FOCUS != 0) {
                    this@ToastWindow.isAlwaysOnTop = true
                }
            }

            override fun windowLostFocus(e: WindowEvent) {
                if (e.newState and WindowEvent.WINDOW_LOST_FOCUS != 0) {
                    this@ToastWindow.isAlwaysOnTop = false
                }
            }
        }.also {
            this.parent.addWindowListener(it)
            this.parent.addWindowStateListener(it)
            this.parent.addWindowFocusListener(it)
        }

        this.parent.addComponentListener(object : ComponentAdapter() {
            override fun componentMoved(e: ComponentEvent) {
                this@ToastWindow.location = Point(parent.x + parent.width - this@ToastWindow.width, parent.y)
            }

            override fun componentResized(e: ComponentEvent) {
                this@ToastWindow.size = Dimension(toastWidth, parent.height)
            }
        })
    }
}