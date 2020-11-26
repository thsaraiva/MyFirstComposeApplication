package com.examples.myfirstcomposeapplication.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import kotlin.math.max

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    children: @Composable () -> Unit
) {
    Layout(
        children = children,
        modifier = modifier
    ) { measurables, constraints ->
        // measure and position children given constraints logic here
        val rowWidths = IntArray(rows) { 0 }
        val rowMaxHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            // measure each child
            measurable.measure(constraints).apply {
                // track width and maxHeight
                val row = index % rows
                rowWidths[row] = rowWidths[row] + width
                rowMaxHeights[row] = max(rowMaxHeights[row], height)
            }
        }

        // calculate grid width and height
        val gridWidth = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth
        val gridHeight = rowMaxHeights.sum().coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Calculate Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowMaxHeights[i - 1]
        }

        // set the size of the parent layout
        layout(gridWidth, gridHeight) {
            // track the x coord we have placed items in a particular row
            val rowX = IntArray(rows) { index ->
                if (index.isEven()) 0 else 32
            }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(rowX[row], rowY[row])
                rowX[row] += placeable.width
            }
        }
    }
}

fun Int.isEven() = this % 2 == 0

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)
