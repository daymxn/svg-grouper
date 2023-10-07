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

import com.daymxn.util.ShapeGrouper
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.parameters.ActionParameter
import org.openrndr.extra.parameters.DoubleParameter

/**
 * Utility object for handling User Interface elements.
 */
object UserInterface {

    /**
     * Populates the UI to a given [GUI].
     *
     * @param gui The GUI object to add elements to.
     * @param grouper The ShapeGrouper object to link actions with.
     *
     * Example:
     * ```
     * val gui = GUI()
     *
     * UserInterface.addToGui(gui, shapeGrouper)
     *
     * extend(gui)
     * ```
     */
    fun addToGui(gui: GUI, grouper: ShapeGrouper) {
        with(gui) {
            add(GUIActions(grouper))
            add(Settings)
        }
    }
}

/**
 * Handles actions to be performed via the GUI.
 *
 * @property grouper The ShapeGrouper object to perform actions on.
 */
class GUIActions(private val grouper: ShapeGrouper) {

    /**
     * Regenerates the shapes based on the set distance in [Settings].
     */
    @ActionParameter("Regen", order = 0)
    fun regen() {
        grouper.groupWithinDistance(Settings.distance)
    }

    /**
     * Exports the grouped shapes to an SVG file.
     */
    @ActionParameter("Export", order = 1)
    fun export() {
        grouper.export()
    }
}

/**
 * Contains settings to be adjusted via the GUI.
 */
object Settings {

    /**
     * The distance parameter for grouping shapes.
     *
     * Range: 0.1 to 10.0, Default: 2.0
     */
    @DoubleParameter("Distance", 0.1, 10.0, 1, order = 0)
    var distance = 2.0
}
