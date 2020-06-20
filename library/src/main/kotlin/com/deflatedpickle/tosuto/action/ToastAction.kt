/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.action

import java.awt.Cursor
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.font.TextAttribute
import javax.swing.Icon
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.UIManager

open class ToastAction(
    text: String,
    icon: Icon?
) : JLabel(text, icon, SwingConstants.LEFT) {
    @Suppress("HasPlatformType")
    private val originalFont = this.font

    private val attributes: MutableMap<*, *> = this.font.attributes

    init {
        @Suppress("UNCHECKED_CAST")
        (attributes as MutableMap<TextAttribute, Any>)[TextAttribute.UNDERLINE] = TextAttribute.UNDERLINE_ON
        val underlineFont = this.font.deriveFont(attributes)

        foreground = UIManager.getColor("List.selectionBackground")
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

        this.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                e.component.font = underlineFont
            }

            override fun mouseExited(e: MouseEvent) {
                e.component.font = originalFont
            }
        })
    }
}
