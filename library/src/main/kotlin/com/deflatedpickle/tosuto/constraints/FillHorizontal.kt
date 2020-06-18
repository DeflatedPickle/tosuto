package com.deflatedpickle.tosuto.constraints

import java.awt.GridBagConstraints
import java.awt.Insets

object FillHorizontal : GridBagConstraints() {
    init {
        anchor = NORTH
        fill = HORIZONTAL
        weightx = 1.0
    }
}