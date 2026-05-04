package com.example.schedulerviewer.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.schedulerviewer.model.LessonStatus

object ScheduleDefaults {
    val CardCornerRadius = 16.dp
    val ItemSpacing = 12.dp
    val ContentPadding = 16.dp
    val HorizontalPadding = 16.dp
}

object LessonCardDefaults {
    fun getCardAlpha(status: LessonStatus): Float = when (status) {
        LessonStatus.PAST -> 0.6f
        LessonStatus.NEXT -> 0.8f
        else -> 1f
    }

    fun getContentType(status: LessonStatus): String = when (status) {
        LessonStatus.CURRENT -> "current_lesson"
        LessonStatus.NEXT -> "next_lesson"
        LessonStatus.PAST -> "past_lesson"
    }

    fun getStatusText(status: LessonStatus): String = when (status) {
        LessonStatus.CURRENT -> "ИДЕТ СЕЙЧАС"
        LessonStatus.NEXT -> "ДАЛЕЕ"
        LessonStatus.PAST -> "ЗАВЕРШЕНО"
    }

    fun isCurrent(status: LessonStatus): Boolean = status.isCurrent()
}

val LessonStatus.shadowElevation: Dp
    get() = if (this.isCurrent()) 4.dp else 0.dp

val LessonStatus.titleFontWeight: FontWeight
    get() = if (this.isPast()) FontWeight.Medium else FontWeight.ExtraBold

val LessonStatus.cardShape
    get() = androidx.compose.foundation.shape.RoundedCornerShape(ScheduleDefaults.CardCornerRadius)
