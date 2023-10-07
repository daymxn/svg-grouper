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

/**
 * Groups lists by shared elements.
 *
 * This function takes a list of lists and returns a new list of lists where
 * each list consists of distinct elements. Lists with shared elements are
 * grouped together.
 *
 * ```
 * val input = listOf(listOf(1, 2), listOf(2, 3), listOf(4, 5))
 * val output = input.groupBySharedElements()
 * // output will be: [[1, 2, 3], [4, 5]]
 * ```
 * @receiver List<List<T>> The input list of lists.
 * @return List<List<T>> A new list of lists with distinct elements, grouped by shared elements.
 *
 */
fun <T> List<List<T>>.groupBySharedElements(): List<List<T>> {
    val result = mutableListOf(mutableListOf<T>())

    for(currentList in this) {
        val matchingLists = result.filter { it.any { currentList.contains(it) } }

        if(matchingLists.isEmpty())
        {
            result.add(currentList.toMutableList())
        } else {
            matchingLists.first().addAll(currentList)
        }
    }

    return result.map { it.distinct() }
}
