/* Copyright (c) 2020 DeflatedPickle under the MIT license */

package com.deflatedpickle.tosuto.constraints

import java.awt.GridBagConstraints

object FillVerticalStickEast : GridBagConstraints() {
    init {
        anchor = EAST
        fill = VERTICAL
        weightx = 1.0
    }
}
