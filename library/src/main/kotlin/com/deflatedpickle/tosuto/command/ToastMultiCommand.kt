/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.command

import com.deflatedpickle.tosuto.api.ToastCommand
import javax.swing.JMenuItem
import javax.swing.JPopupMenu

class ToastMultiCommand(
    override val text: String,
    private val actions: Set<JMenuItem>
) : ToastCommand {
    @Suppress("unused")
    constructor(text: String) : this(text, setOf())

    val menu = JPopupMenu().apply {
        for (i in actions) {
            add(i)
        }
    }
}
