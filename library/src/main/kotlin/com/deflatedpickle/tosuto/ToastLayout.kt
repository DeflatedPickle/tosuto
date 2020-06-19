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
class ToastLayout(
    private val spacing: Int = 2,
    private val westMargin: Int = 2,
    private val northMargin: Int = 2,
    private val eastMargin: Int = 2,
    private val southMargin: Int = 2,
    private val order: ToastOrder = ToastOrder.ITERATIVE,
    private val anchor: ToastItemAnchor = ToastItemAnchor.SOUTH
) : LayoutManager {
    @Suppress("unused")
    constructor(
        margin: Int,
        order: ToastOrder
    ) : this(
        westMargin = margin,
        northMargin = margin,
        eastMargin = margin,
        southMargin = margin,
        order = order
    )

    @Suppress("unused")
    constructor(
        horizontalMargin: Int,
        verticalMargin: Int,
        order: ToastOrder
    ) : this(
        westMargin = horizontalMargin,
        northMargin = verticalMargin,
        eastMargin = horizontalMargin,
        southMargin = verticalMargin,
        order = order
    )

    override fun layoutContainer(parent: Container) {
        var height = when (this.anchor) {
            ToastItemAnchor.NORTH -> 0
            ToastItemAnchor.SOUTH -> parent.components.foldRight(0, { component, acc ->
                acc + component.preferredSize.height + spacing
            })
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
                parent.width - parent.insets.right - eastMargin,
                comp.preferredSize.height - southMargin
            )

            when (this.anchor) {
                ToastItemAnchor.NORTH ->
                    height += comp.preferredSize.height
                ToastItemAnchor.SOUTH ->
                    height -= comp.preferredSize.height
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
