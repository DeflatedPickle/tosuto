package com.deflatedpickle.tosuto

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager
import java.awt.Rectangle

/**
 * A custom layout manager to lay toasts out vertically, from the bottom
 */
// TODO: Use the preferred height instead of a single value
class ToastLayout(
    private val toastHeight: Int = 80,
    private val spacing: Int = 6,
    private val westMargin: Int = 6,
    private val northMargin: Int = 6,
    private val eastMargin: Int = 6,
    private val southMargin: Int = 6
) : LayoutManager {
    @Suppress("unused")
    constructor(
        margin: Int
    ) : this(
        westMargin = margin,
        northMargin = margin,
        eastMargin = margin,
        southMargin = margin
    )

    @Suppress("unused")
    constructor(
        horizontalMargin: Int,
        verticalMargin: Int
    ) : this(
        westMargin = horizontalMargin,
        northMargin = verticalMargin,
        eastMargin = horizontalMargin,
        southMargin = verticalMargin
    )

    override fun layoutContainer(parent: Container) {
        for ((i, comp) in parent.components.withIndex()) {
            comp.bounds = Rectangle(
                parent.insets.left + westMargin,
                parent.height - ((toastHeight + spacing) * (i + 1)) + northMargin,
                parent.width - parent.insets.right - eastMargin,
                toastHeight - southMargin
            )
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