package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.api.ToastOrder
import com.deflatedpickle.tosuto.api.ToastWindowAnchor
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
    val windowAnchor: ToastWindowAnchor = ToastWindowAnchor.EAST,
    val toastOrder: ToastOrder = ToastOrder.ITERATIVE
) : JDialog(parent) {
    init {
        this.isUndecorated = true
        this.background = Color(0, 0, 0, 0)

        this.contentPane.layout = ToastLayout(order = this.toastOrder)

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
                this@ToastWindow.location = when (this@ToastWindow.windowAnchor) {
                    ToastWindowAnchor.CENTRE -> Point(parent.x + parent.width / 2 - this@ToastWindow.width / 2, parent.y)
                    ToastWindowAnchor.EAST -> Point(parent.x + parent.width - this@ToastWindow.width, parent.y)
                    ToastWindowAnchor.WEST -> Point(parent.x, parent.y)
                }
            }

            override fun componentResized(e: ComponentEvent) {
                this@ToastWindow.size = Dimension(toastWidth, parent.height)
                this.componentMoved(e)

                this@ToastWindow.doLayout()
                this@ToastWindow.repaint()
                this@ToastWindow.revalidate()
            }
        })
    }
}