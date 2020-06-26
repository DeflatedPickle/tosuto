/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.constraints

import java.awt.GridBagConstraints

object FillHorizontal : GridBagConstraints() {
    init {
        anchor = NORTH
        fill = HORIZONTAL
        weightx = 1.0
    }
}
