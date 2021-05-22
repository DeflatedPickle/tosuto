/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto

import com.deflatedpickle.tosuto.api.ToastItemAnchor
import com.deflatedpickle.tosuto.api.ToastOrder
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager
import java.awt.Rectangle

/**
 * A custom layout manager to lay toasts out vertically, from the bottom
 */
class ToastLayout @JvmOverloads constructor(
    var spacing: Int = 2,
    var westMargin: Int = 0,
    var northMargin: Int = 0,
    var eastMargin: Int = 0,
    var southMargin: Int = 0,
    var order: ToastOrder = ToastOrder.ITERATIVE,
    var anchor: ToastItemAnchor = ToastItemAnchor.SOUTH
) : LayoutManager {
    override fun layoutContainer(parent: Container) {
        var height = when (this.anchor) {
            ToastItemAnchor.NORTH -> 0
            ToastItemAnchor.SOUTH -> parent.components.foldRight(0) { component, acc ->
                acc + (component.preferredSize.height - northMargin - southMargin) + spacing
            }
        }

        val components = when (this.order) {
            ToastOrder.ITERATIVE -> parent.components
            ToastOrder.REVERSE -> parent.components.reversedArray()
        }

        for (comp in components) {
            comp.bounds = Rectangle(
                parent.insets.left + westMargin,
                when (this.anchor) {
                    ToastItemAnchor.NORTH -> height
                    ToastItemAnchor.SOUTH -> parent.height - height
                } + northMargin,
                parent.width - parent.insets.right - westMargin - eastMargin,
                when (this.anchor) {
                    ToastItemAnchor.NORTH ->
                        comp.preferredSize.height - northMargin - southMargin
                    ToastItemAnchor.SOUTH ->
                        comp.preferredSize.height - northMargin - southMargin
                }
            )

            when (this.anchor) {
                ToastItemAnchor.NORTH -> height += comp.preferredSize.height + spacing
                ToastItemAnchor.SOUTH -> height -= comp.preferredSize.height + spacing
            }
        }
    }

    override fun preferredLayoutSize(parent: Container): Dimension = with(parent.preferredSize) {
        Dimension(
            this.width + westMargin + eastMargin,
            this.height + northMargin + southMargin
        )
    }

    override fun minimumLayoutSize(parent: Container): Dimension = with(parent.minimumSize) {
        Dimension(
            this.width + westMargin + eastMargin,
            this.height + northMargin + southMargin
        )
    }

    override fun addLayoutComponent(name: String, comp: Component) {
    }

    override fun removeLayoutComponent(comp: Component) {
    }
}
