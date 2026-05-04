package com.example.schedulerviewer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.schedulerviewer.model.Lesson
import com.example.schedulerviewer.model.LessonStatus

@Composable
fun LessonCard(lesson: Lesson, showStatus: Boolean = true) {
    val isCurrent = showStatus && lesson.status == LessonStatus.CURRENT
    val alpha = if (showStatus) 1f else 0.6f
    val outlineColor = if (isCurrent)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    else
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = alpha),
        border = BorderStroke(1.dp, outlineColor.copy(alpha = alpha)),
        shadowElevation = if (isCurrent) 4.dp else 0.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (showStatus) {
                LessonStatusHeader(lesson)
                Spacer(modifier = Modifier.height(4.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Normal,
                    modifier = Modifier.alpha(alpha).weight(1f)
                )
                if (!showStatus) {
                    Text(
                        text = lesson.timeRange,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            LessonDetailsRow(lesson, showStatus)
        }
    }
}

@Composable
private fun LessonStatusHeader(lesson: Lesson) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isCurrent = lesson.status == LessonStatus.CURRENT

        Text(
            text = LessonCardDefaults.getStatusText(lesson.status),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isCurrent) FontWeight.Black else FontWeight.Bold,
            color = if (isCurrent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .background(
                    color = if (isCurrent) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Text(
            text = lesson.timeRange,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LessonDetailsRow(lesson: Lesson, showStatus: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LessonDetailItem(Icons.Default.LocationOn, lesson.room)
        lesson.teacher?.let { LessonDetailItem(Icons.Default.Person, it) }

        if (showStatus) {
            val timeRemaining = lesson.timeRemaining
            if (timeRemaining != null) {
                Spacer(modifier = Modifier.weight(1f))
                TimeRemainingBadge(timeRemaining)
            }
        }
    }
}

@Composable
fun LessonDetailItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TimeRemainingBadge(text: String) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = CircleShape,
        color = Color.Transparent
    ) {
        Text(
            text = "осталось $text мин",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
