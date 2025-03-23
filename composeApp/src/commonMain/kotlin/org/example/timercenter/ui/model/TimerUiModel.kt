package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerEntity

@Stable
data class TimerUiModel(
    val id: Int = -1,
    val timerName: String = "",
    val totalTime: Long = 60_000L,
    val groupId: Int? = null,
    val lastStartedTime: Long = 0L // Время последнего запуска в миллисекундах
)

fun TimerEntity.toUiModel(): TimerUiModel {
    return TimerUiModel(
        id = id,
        timerName = name,
        totalTime = durationMillis,
        groupId = groupId,
        lastStartedTime = startTime ?: 0L
    )
}

