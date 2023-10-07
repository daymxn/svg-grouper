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

import org.openrndr.shape.Shape

/**
 * Filters a list of shapes that are within a certain distance of the current shape.
 *
 * ```
 * val closeShapes = shape1.withinDistance(listOf(shape2, shape3), 10.0)
 * ```
 *
 * @param others List of shapes to compare against.
 * @param distance The maximum distance for shapes to be considered within range.
 * @return List of shapes that are within the given distance of the current shape.
 */
fun Shape.withinDistance(others: List<Shape>, distance: Double): List<Shape> {
    val targetArea = bounds.offsetEdges(distance)

    return others.filter { it.bounds.offsetEdges(distance).intersects(targetArea) }
}

