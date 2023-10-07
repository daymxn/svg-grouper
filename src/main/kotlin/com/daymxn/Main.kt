// Copyright 2023 Daymon Littrell-Reyes
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.daymxn

import com.daymxn.util.*
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.GHOST_WHITE
import org.openrndr.extra.gui.GUI
import java.io.File

fun main() = application {
    configure {
        width = 900
        height = 900
        windowResizable = true
        title = "SVG Grouper"
        vsync = true
    }
    program {
        val grouper = ShapeGrouper()

        window.drop.listen {
            grouper.loadFile(File(it.files.first()))
        }

        val gui = GUI().apply {
            showToolbar = false
            persistState = false
            compartmentsCollapsedByDefault = false
        }

        UserInterface.addToGui(gui, grouper)

        extend(gui)

        extend {
            drawer.clear(ColorRGBa.GHOST_WHITE)

            grouper.draw()
        }
    }
}
