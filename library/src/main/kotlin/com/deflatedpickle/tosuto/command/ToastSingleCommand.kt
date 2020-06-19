/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.command

import com.deflatedpickle.tosuto.api.ToastCommand

class ToastSingleCommand(
    override val text: String,
    val command: () -> Unit
) : ToastCommand {
    @Suppress("unused")
    constructor(text: String) : this(text, {})
}
