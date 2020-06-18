package com.deflatedpickle.tosuto.command

import com.deflatedpickle.tosuto.api.ToastCommand
import javax.swing.JButton

class ToastSingleCommand(
    override val text: String,
    val command: () -> Unit
) : ToastCommand {
    constructor(text: String) : this(text, {})
}