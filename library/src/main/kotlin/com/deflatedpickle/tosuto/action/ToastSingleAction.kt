/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.action

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Icon

class ToastSingleAction @JvmOverloads constructor(
    text: String,
    icon: Icon? = null,
    command: () -> Unit
) : ToastAction(text, icon) {
    init {
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                command()
            }
        })
    }
}
