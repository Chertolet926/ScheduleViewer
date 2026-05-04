package com.example.schedulerviewer.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T : Any> SlidingSelectorLayout(
    items: List<T>,
    selectedItem: T,
    modifier: Modifier = Modifier,
    selectorHeight: Dp = 52.dp,
    selectorContent: @Composable (BoxWithConstraintsScope, Dp) -> Unit,
    content: @Composable RowScope.(T, Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(selectorHeight)
    ) {
        val selectedIndex = items.indexOf(selectedItem).coerceAtLeast(0)
        val offsetBy by animateDpAsState(
            targetValue = (maxWidth / items.size) * selectedIndex,
            animationSpec = spring(
                dampingRatio = 0.8f,
                stiffness = Spring.StiffnessMediumLow
            ),
            label = "slidingOffset"
        )

        selectorContent(this, offsetBy)

        Row(modifier = Modifier.fillMaxSize()) {
            items.forEach { item ->
                content(item, item == selectedItem)
            }
        }
    }
}
