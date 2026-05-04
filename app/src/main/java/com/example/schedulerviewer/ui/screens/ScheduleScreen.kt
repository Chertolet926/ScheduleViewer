package com.example.schedulerviewer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schedulerviewer.model.Lesson
import com.example.schedulerviewer.model.LessonStatus
import com.example.schedulerviewer.model.WeekType
import com.example.schedulerviewer.model.getCurrentWeekType
import com.example.schedulerviewer.ui.components.DaySelectorList
import com.example.schedulerviewer.ui.components.LessonCard
import com.example.schedulerviewer.ui.components.LessonCardDefaults
import com.example.schedulerviewer.ui.components.WeekToggle
import com.example.schedulerviewer.viewmodel.ScheduleViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun VerticalHeaderSelector(
    selectedDay: DayOfWeek,
    isUpperWeek: Boolean,
    onDaySelected: (DayOfWeek) -> Unit,
    onWeekToggle: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0C0C0C))
            .padding(top = 12.dp)
    ) {
        WeekToggle(isUpperWeek = isUpperWeek, onToggle = onWeekToggle)
        DaySelectorList(selectedDay = selectedDay, onDaySelected = onDaySelected)
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF1A1A1A))
        )
    }
}

@Composable
fun ScheduleMobileContent(viewModel: ScheduleViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lessons = viewModel.getLessonsForDay(uiState.selectedDay)
    val currentDay = java.time.LocalDate.now().dayOfWeek
    val isCurrentDay = uiState.selectedDay == currentDay
    val currentWeekType = getCurrentWeekType()
    val selectedWeekType = if (uiState.isUpperWeek) WeekType.UPPER else WeekType.LOWER
    val showStatus = isCurrentDay && currentWeekType == selectedWeekType

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C0C))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            VerticalHeaderSelector(
                selectedDay = uiState.selectedDay,
                isUpperWeek = uiState.isUpperWeek,
                onDaySelected = { viewModel.selectDay(it) },
                onWeekToggle = { viewModel.toggleWeek(it) }
            )

            ScheduleList(lessons = lessons, showStatus = showStatus)
        }
    }
}

@Composable
private fun ScheduleList(lessons: List<Lesson>, showStatus: Boolean) {
    val listState = rememberLazyListState()

    LaunchedEffect(lessons, showStatus) {
        if (lessons.isEmpty() || !showStatus) return@LaunchedEffect

        val targetIndex = lessons.indexOfFirst { it.status == LessonStatus.CURRENT }
            .let { if (it == -1) lessons.indexOfFirst { l -> l.status == LessonStatus.NEXT } else it }
            .coerceAtLeast(0)

        if (targetIndex > 0) {
            listState.scrollToItem(targetIndex, scrollOffset = -30)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = lessons,
            key = { it.id },
            contentType = { lesson ->
                val status = lesson.status
                LessonCardDefaults.getContentType(status)
            }
        ) { lesson ->
            LessonCard(lesson = lesson, showStatus = showStatus)
        }
    }
}

@Composable
private fun LoadingStub() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "ЗАГРУЗКА",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray.copy(alpha = 0.4f),
            letterSpacing = 2.sp
        )
    }
}

@Composable
fun ScheduleTabletContent() { /* pass */ }

@Composable
fun ScheduleScreen(
    widthSizeClass: WindowWidthSizeClass,
    viewModel: ScheduleViewModel = viewModel()
) {
    when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> ScheduleMobileContent(viewModel)
        else -> ScheduleTabletContent()
    }
}
