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
    val spacing: Int = 2,
    private val westMargin: Int = 2,
    private val northMargin: Int = 2,
    private val eastMargin: Int = 2,
    private val southMargin: Int = 2
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
        var height = parent.components.foldRight(0, { component, acc ->
            acc + component.preferredSize.height + spacing
        })

        for ((_, comp) in parent.components.withIndex()) {
            comp.bounds = Rectangle(
                parent.insets.left + westMargin,
                parent.height - height + northMargin,
                parent.width - parent.insets.right - eastMargin,
                comp.preferredSize.height - southMargin
            )

            height -= comp.preferredSize.height
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