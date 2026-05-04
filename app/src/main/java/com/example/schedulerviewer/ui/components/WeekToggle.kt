package com.example.schedulerviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeekToggle(
    isUpperWeek: Boolean,
    onToggle: (Boolean) -> Unit
) {
    SlidingSelectorLayout(
        items = listOf(true, false),
        selectedItem = isUpperWeek,
        selectorHeight = 48.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF161616))
            .padding(4.dp),
        selectorContent = { scope, offset ->
            Box(
                modifier = Modifier
                    .offset { IntOffset(offset.roundToPx(), 0) }
                    .clip(RoundedCornerShape(12.dp))
                    .width(scope.maxWidth / 2)
                    .fillMaxHeight()
                    .background(Color.White)
            )
        }
    ) { isUpper, isSelected ->
        val text = if (isUpper) "НАД ЧЕРТОЙ" else "под чертой"
        val letterSpacing = if (isSelected) 0.5.sp else 0.sp

        SelectorItem(
            text = text,
            isSelected = isSelected,
            onClick = { onToggle(isUpper) },
            letterSpacing = letterSpacing
        )
    }
}
