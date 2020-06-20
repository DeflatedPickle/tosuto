/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.action

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Icon
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.SwingConstants
import javax.swing.UIManager

class ToastMultiAction(
    text: String,
    icon: Icon? = UIManager.getIcon("Table.descendingSortIcon"),
    actions: List<JMenuItem>
) : ToastAction(text, icon) {
    constructor(
        text: String,
        actions: List<JMenuItem>
    ) : this(
        text = text,
        icon = UIManager.getIcon("Table.descendingSortIcon"),
        actions = actions
    )

    val menu = JPopupMenu().apply {
        for (i in actions) {
            add(i)
        }
    }

    init {
        this.horizontalTextPosition = SwingConstants.LEFT

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                this@ToastMultiAction.menu.show(
                    this@ToastMultiAction,
                    e.component.x - e.component.width,
                    e.component.y + e.component.height
                )
            }
        })
    }
}
