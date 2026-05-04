package com.example.schedulerviewer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.schedulerviewer.model.Lesson
import com.example.schedulerviewer.model.Schedule
import com.example.schedulerviewer.model.ScheduleDay
import com.example.schedulerviewer.model.WeekType
import java.time.LocalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate

fun getDummySchedule(): Schedule {
    val mondayLessons = listOf(
        Lesson(id = 1, title = "Алгебра", startTime = LocalTime.of(8, 0), endTime = LocalTime.of(9, 30), room = "101", teacher = "Иванов И.И.", weekType = WeekType.UPPER),
        Lesson(id = 2, title = "Геометрия", startTime = LocalTime.of(10, 0), endTime = LocalTime.of(11, 30), room = "202", teacher = "Петров П.П.", weekType = WeekType.LOWER),
        Lesson(id = 3, title = "Программирование", startTime = LocalTime.of(12, 0), endTime = LocalTime.of(13, 30), room = "305", teacher = "Сидоров С.С.", weekType = WeekType.BOTH),
        Lesson(id = 4, title = "Базы данных", startTime = LocalTime.of(14, 0), endTime = LocalTime.of(15, 30), room = "401", teacher = "Николаев Н.Н.", weekType = WeekType.UPPER),
        Lesson(id = 5, title = "Веб-разработка", startTime = LocalTime.of(16, 0), endTime = LocalTime.of(17, 30), room = "502", teacher = "Михайлов М.М.", weekType = WeekType.LOWER),
        Lesson(id = 6, title = "Английский язык", startTime = LocalTime.of(18, 0), endTime = LocalTime.of(19, 30), room = "105", teacher = "Smith J.", weekType = WeekType.BOTH)
    )

    val tuesdayLessons = listOf(
        Lesson(id = 7, title = "Физика", startTime = LocalTime.of(9, 0), endTime = LocalTime.of(10, 30), room = "301", teacher = "Физиков Ф.Ф.", weekType = WeekType.BOTH),
        Lesson(id = 8, title = "Химия", startTime = LocalTime.of(12, 0), endTime = LocalTime.of(13, 30), room = "Лаб-2", teacher = "Химиков Х.Х.", weekType = WeekType.UPPER),
        Lesson(id = 9, title = "Биология", startTime = LocalTime.of(14, 0), endTime = LocalTime.of(15, 30), room = "Лаб-3", teacher = "Биологов Б.Б.", weekType = WeekType.LOWER),
        Lesson(id = 10, title = "Экология", startTime = LocalTime.of(16, 0), endTime = LocalTime.of(17, 30), room = "205", teacher = "Экологов Э.Э.", weekType = WeekType.BOTH)
    )

    val wednesdayLessons = listOf(
        Lesson(id = 11, title = "История", startTime = LocalTime.of(8, 0), endTime = LocalTime.of(9, 30), room = "401", teacher = "Историков И.И.", weekType = WeekType.LOWER),
        Lesson(id = 12, title = "Обществознание", startTime = LocalTime.of(10, 0), endTime = LocalTime.of(11, 30), room = "402", teacher = "Обществов О.О.", weekType = WeekType.UPPER),
        Lesson(id = 13, title = "География", startTime = LocalTime.of(12, 0), endTime = LocalTime.of(13, 30), room = "403", teacher = "Географов Г.Г.", weekType = WeekType.BOTH),
        Lesson(id = 14, title = "Литература", startTime = LocalTime.of(14, 0), endTime = LocalTime.of(15, 30), room = "404", teacher = "Литератор Л.Л.", weekType = WeekType.UPPER),
        Lesson(id = 15, title = "Музыка", startTime = LocalTime.of(16, 0), endTime = LocalTime.of(17, 30), room = "Муз-1", teacher = "Музыкант М.М.", weekType = WeekType.LOWER)
    )

    val thursdayLessons = listOf(
        Lesson(id = 16, title = "Физкультура", startTime = LocalTime.of(9, 0), endTime = LocalTime.of(10, 30), room = "Спортзал", teacher = "Тренер Т.Т.", weekType = WeekType.BOTH),
        Lesson(id = 17, title = "Математический анализ", startTime = LocalTime.of(12, 0), endTime = LocalTime.of(13, 30), room = "501", teacher = "Математик М.М.", weekType = WeekType.UPPER),
        Lesson(id = 18, title = "Дискретная математика", startTime = LocalTime.of(14, 0), endTime = LocalTime.of(15, 30), room = "502", teacher = "Дискрет Д.Д.", weekType = WeekType.LOWER),
        Lesson(id = 19, title = "Теория вероятностей", startTime = LocalTime.of(16, 0), endTime = LocalTime.of(17, 30), room = "503", teacher = "Вероятнов В.В.", weekType = WeekType.BOTH)
    )

    val fridayLessons = listOf(
        Lesson(id = 20, title = "Экономика", startTime = LocalTime.of(8, 0), endTime = LocalTime.of(9, 30), room = "601", teacher = "Экономов Э.Э.", weekType = WeekType.UPPER),
        Lesson(id = 21, title = "Право", startTime = LocalTime.of(10, 0), endTime = LocalTime.of(11, 30), room = "602", teacher = "Правов П.П.", weekType = WeekType.LOWER),
        Lesson(id = 22, title = "Социология", startTime = LocalTime.of(12, 0), endTime = LocalTime.of(13, 30), room = "603", teacher = "Социолог С.С.", weekType = WeekType.BOTH),
        Lesson(id = 23, title = "Психология", startTime = LocalTime.of(14, 0), endTime = LocalTime.of(15, 30), room = "604", teacher = "Психолог П.П.", weekType = WeekType.UPPER),
        Lesson(id = 24, title = "Философия", startTime = LocalTime.of(16, 0), endTime = LocalTime.of(17, 30), room = "605", teacher = "Философ Ф.Ф.", weekType = WeekType.LOWER)
    )

    val saturdayLessons = listOf(
        Lesson(id = 25, title = "Черчение", startTime = LocalTime.of(9, 0), endTime = LocalTime.of(10, 30), room = "Чер-1", teacher = "Чертежов Ч.Ч.", weekType = WeekType.BOTH),
        Lesson(id = 26, title = "Дизайн", startTime = LocalTime.of(12, 0), endTime = LocalTime.of(13, 30), room = "Диз-1", teacher = "Дизайнер Д.Д.", weekType = WeekType.UPPER),
        Lesson(id = 27, title = "Архитектура", startTime = LocalTime.of(14, 0), endTime = LocalTime.of(15, 30), room = "Арх-1", teacher = "Архитектор А.А.", weekType = WeekType.LOWER)
    )

    val days = listOf(
        ScheduleDay(day = DayOfWeek.MONDAY, lessons = mondayLessons),
        ScheduleDay(day = DayOfWeek.TUESDAY, lessons = tuesdayLessons),
        ScheduleDay(day = DayOfWeek.WEDNESDAY, lessons = wednesdayLessons),
        ScheduleDay(day = DayOfWeek.THURSDAY, lessons = thursdayLessons),
        ScheduleDay(day = DayOfWeek.FRIDAY, lessons = fridayLessons),
        ScheduleDay(day = DayOfWeek.SATURDAY, lessons = saturdayLessons)
    )

    return Schedule(days = days)
}

data class ScheduleUiState(
    val selectedDay: DayOfWeek = LocalDate.now().dayOfWeek,
    val isUpperWeek: Boolean = true
)

class ScheduleViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState

    private val _schedule = getDummySchedule()
    val schedule: Schedule = _schedule

    fun selectDay(day: DayOfWeek) { _uiState.update { it.copy(selectedDay = day) } }
    fun toggleWeek(isUpper: Boolean) { _uiState.update { it.copy(isUpperWeek = isUpper) } }

    fun getLessonsForDay(day: DayOfWeek): List<Lesson> {
        val isUpperWeek = _uiState.value.isUpperWeek
        return schedule.getDay(day)?.lessons?.filter { it.weekType.matches(isUpperWeek) } ?: emptyList()
    }
}