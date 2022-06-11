package com.mikhailovalx.composegraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

@Composable
fun MeasureComposableHeight(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredHeight: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredHeight = subcompose("viewToMeasure", viewToMeasure)
            .first()
            .measure(Constraints()).height.toDp()

        val contentPlaceable = subcompose("content") { content(measuredHeight) }
            .first()
            .measure(constraints)

        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}