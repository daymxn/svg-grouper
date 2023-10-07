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

package com.daymxn.util

import org.openrndr.Program
import org.openrndr.shape.CompositionDrawer
import org.openrndr.shape.Shape
import org.openrndr.shape.compound
import org.openrndr.svg.loadSVG
import org.openrndr.svg.writeSVG
import java.io.File

/**
 * Groups shapes based distance.
 *
 * @property originals The original list of shapes to be grouped.
 * @property shapes The modified list of shapes after grouping.
 * @property file The SVG file that the shapes were loaded from (and will be exported to)
 *
 * @see groupWithinDistance
 */
class ShapeGrouper {
    private var originals = emptyList<Shape>()
    private var shapes = originals
    private var file = File("")

    /**
     * Groups shapes that are within a certain distance of each other.
     *
     * The function updates the [shapes] property with the new grouped shapes.
     *
     * ```
     * val shapeGrouper = ShapeGrouper(listOf(shape1, shape2))
     * shapeGrouper.groupWithinDistance(10.0)
     * ```
     *
     * @param distance The maximum distance between shapes for them to be considered in the same group.
     */
    fun groupWithinDistance(distance: Double) {
        println("Grouping...")

        with(originals) {
            val shapesWithinDistance = windowed(size, partialWindows = true) {
                val current = it.first()
                val others = it.drop(1)

                current.withinDistance(others, distance) + current
            }
            val reduced = shapesWithinDistance.groupBySharedElements()

            shapes = reduced.map { it.compound }
        }

        println("Grouped!")
    }

    /**
     * Loads a new SVG file and populates the [originals] and [shapes] properties.
     *
     * @param newFile The SVG file to load.
     *
     * Example:
     * ```
     * shapeGrouper.loadFile(File("path/to/file.svg"))
     * ```
     */
    fun loadFile(newFile: File) {
        file = newFile
        val svg = loadSVG(file)
        originals = svg.findShapes().map { it.shape }
        shapes = originals
    }

    /**
     * Exports the grouped shapes back to the original SVG file.
     */
    fun export() {
        println("Exporting...")

        val initialSvg = loadSVG(file)
        val comp = CompositionDrawer(initialSvg.bounds)

        for(shape in shapes) {
            comp.shape(shape)
        }

        file.writeText(writeSVG(comp.composition))

        println("Exported!")
    }

    /**
     * Draws all shapes in the [shapes] property using a given [drawer][Program.drawer].
     *
     * This function is usually called within the draw loop of the program.
     *
     * @receiver The program's drawing context.
     *
     * Example:
     * ```
     * program {
     *  shapeGrouper.draw()
     * }
     * ```
     */
    context(Program)
    fun draw() {
        for(shape in shapes) {
            drawer.shape(shape)
        }
    }
}
