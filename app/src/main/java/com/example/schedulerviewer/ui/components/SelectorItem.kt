package com.example.schedulerviewer.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.SelectorItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedFontSize: TextUnit = 13.sp,
    unselectedFontSize: TextUnit = 13.sp,
    letterSpacing: TextUnit = 0.sp,
    animateWeight: Boolean = false
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.White.copy(alpha = 0.4f),
        animationSpec = tween(250),
        label = "selectorColor"
    )

    val fontSize by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isSelected) selectedFontSize.value else unselectedFontSize.value,
        animationSpec = tween(250),
        label = "selectorFontSize"
    )

    val fontWeight = if (animateWeight) {
        androidx.compose.animation.core.animateIntAsState(
            targetValue = if (isSelected) FontWeight.Black.weight else FontWeight.Bold.weight,
            animationSpec = androidx.compose.animation.core.spring(
                androidx.compose.animation.core.Spring.DampingRatioLowBouncy
            ),
            label = "selectorFontWeight"
        ).value.let { FontWeight(it) }
    } else {
        if (isSelected) FontWeight.Black else FontWeight.Bold
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = text,
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            letterSpacing = letterSpacing,
            softWrap = false,
            textAlign = TextAlign.Center
        )
    }
}
