package com.example.schedulerviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schedulerviewer.model.shortDisplayName
import java.time.DayOfWeek

@Composable
fun DaySelectorList(
    modifier: Modifier = Modifier,
    selectedDay: DayOfWeek,
    onDaySelected: (DayOfWeek) -> Unit
) {
    val days = remember { DayOfWeek.entries.take(6) }

    SlidingSelectorLayout(
        items = days,
        selectedItem = selectedDay,
        selectorHeight = 60.dp,
        modifier = modifier
            .background(Color(0xFF0C0C0C))
            .padding(vertical = 12.dp),
        selectorContent = { scope, offset ->
            val tabWidth = scope.maxWidth / days.size
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .offset { IntOffset((offset + (tabWidth / 2) - 28.dp).roundToPx(), 0) }
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            )
        }
    ) { day, isSelected ->
        val dayName = remember(day) { day.shortDisplayName() }
        SelectorItem(
            text = dayName,
            isSelected = isSelected,
            onClick = { onDaySelected(day) },
            selectedFontSize = 17.sp,
            unselectedFontSize = 15.sp,
            animateWeight = true
        )
    }
}
