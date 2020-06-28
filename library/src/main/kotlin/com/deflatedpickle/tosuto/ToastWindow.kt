/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.api.ToastItemAnchor
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
class ToastWindow @JvmOverloads constructor(
    @Suppress("MemberVisibilityCanBePrivate")
    val parent: Frame,
    toastWidth: Int = 140,
    toastOrder: ToastOrder = ToastOrder.ITERATIVE,
    toastAnchor: ToastItemAnchor = ToastItemAnchor.SOUTH,
    windowAnchor: ToastWindowAnchor = ToastWindowAnchor.EAST
) : JDialog(parent) {
    var toastWidth = toastWidth
        set(value) {
            this.locateToParent()
            this.size = Dimension(value, parent.height)
            this.refresh()

            field = value
        }

    var toastOrder = toastOrder
        set(value) {
            (this.contentPane.layout as ToastLayout).order = value
            this.refresh()

            field = value
        }

    var toastAnchor = toastAnchor
        set(value) {
            (this.contentPane.layout as ToastLayout).anchor = value
            this.refresh()

            field = value
        }

    var windowAnchor = windowAnchor
        set(value) {
            field = value

            this.locateToParent()
        }

    init {
        this.isUndecorated = true
        this.background = Color(0, 0, 0, 0)

        this.contentPane.layout = ToastLayout(order = this.toastOrder, anchor = this.toastAnchor)

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
                locateToParent()
            }

            override fun componentResized(e: ComponentEvent) {
                this@ToastWindow.size = Dimension(this@ToastWindow.toastWidth, parent.height)
                locateToParent()

                this@ToastWindow.refresh()
            }
        })
    }

    @Suppress("unused")
    fun addToast(toast: ToastItem) {
        this.add(toast)
        this.refresh()
    }

    @Suppress("unused")
    fun removeToast(toast: ToastItem) {
        this.remove(toast)
        this.refresh()
    }

    @Suppress("unused")
    fun removeToast(toastIndex: Int) {
        this.remove(toastIndex)
        this.refresh()
    }

    fun refresh() {
        this.contentPane.doLayout()
        this.contentPane.repaint()
        this.contentPane.revalidate()

        this.repaint()
        this.revalidate()
    }

    fun locateToParent() {
        this@ToastWindow.location = when (this@ToastWindow.windowAnchor) {
            ToastWindowAnchor.CENTRE -> Point(parent.x + parent.width / 2 - this@ToastWindow.width / 2, parent.y)
            ToastWindowAnchor.EAST -> Point(parent.x + parent.width - this@ToastWindow.width, parent.y)
            ToastWindowAnchor.WEST -> Point(parent.x, parent.y)
        }
    }
}
