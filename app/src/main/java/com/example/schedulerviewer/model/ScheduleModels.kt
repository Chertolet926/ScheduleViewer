package com.example.schedulerviewer.model

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

object TimeProvider {
    var mockTime: LocalTime? = LocalTime.of(12, 30)
}

fun getCurrentWeekType(): WeekType {
    val weekNumber = java.time.LocalDate.now().get(java.time.temporal.WeekFields.ISO.weekOfYear())
    return if (weekNumber % 2 == 0) WeekType.UPPER else WeekType.LOWER
}

data class Schedule(
    val days: List<ScheduleDay> = emptyList()
) {
    fun getDay(day: DayOfWeek): ScheduleDay? = days.find { it.day == day }
}

data class ScheduleDay(
    val day: DayOfWeek,
    val lessons: List<Lesson> = emptyList()
)

data class Lesson(
    val id: Int,
    val title: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val room: String,
    val teacher: String? = null,
    val weekType: WeekType = WeekType.BOTH
) {
    val timeRange: String
        get() = "${startTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))} — ${endTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))}"

    val status: LessonStatus
        get() {
            val now = TimeProvider.mockTime ?: LocalTime.now()
            return when {
                now.isBefore(startTime) -> LessonStatus.NEXT
                now.isAfter(endTime) -> LessonStatus.PAST
                else -> LessonStatus.CURRENT
            }
        }

    val timeRemaining: String?
        get() {
            if (status != LessonStatus.CURRENT) return null
            val now = TimeProvider.mockTime ?: LocalTime.now()
            return when {
                now.isBefore(startTime) -> Duration.between(now, startTime).toMinutes().let { "${it / 60}:${"%02d".format(it % 60)}" }
                else -> Duration.between(now, endTime).toMinutes().let { "${it / 60}:${"%02d".format(it % 60)}" }
            }
        }
}

enum class WeekType(val value: Int) {
    UPPER(0),
    LOWER(1),
    BOTH(2);

    fun matches(isUpperWeek: Boolean): Boolean {
        return this == BOTH || (this == UPPER && isUpperWeek) || (this == LOWER && !isUpperWeek)
    }
}

enum class LessonStatus {
    CURRENT,
    NEXT,
    PAST;

    fun isCurrent(): Boolean = this == CURRENT
    fun isPast(): Boolean = this == PAST
    fun isNext(): Boolean = this == NEXT

    val displayPriority: Int
        get() = when (this) {
            CURRENT -> 0
            NEXT -> 1
            PAST -> 2
        }
}

fun DayOfWeek.shortDisplayName(locale: Locale = Locale.getDefault()): String =
    getDisplayName(TextStyle.SHORT, locale).uppercase()
