package org.example.timercenter.ui.model

import kotlin.random.Random

data class TimerUiModel(
    val id: Int,
    val timerName: String,
    val totalTime: Long = 60_000L,
    val lastStartedTime: Long = 0L // Время последнего запуска в миллисекундах
)

