package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable

@Stable
data class TimerUiModel(
    val id: Int = -1,
    val timerName: String = "",
    val totalTime: Long = 60_000L,
    val groupId: Long,
    val lastStartedTime: Long = 0L // Время последнего запуска в миллисекундах
)

