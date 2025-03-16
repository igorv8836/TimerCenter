package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable


enum class GroupType {
    CONSISTENT,
    PARALLEL,
    DELAY
}

@Stable
data class TimerGroupUiModel(
    val id: Int,
    val groupName: String,
    val groupType: GroupType,
    val timers: List<TimerUiModel>,
    val lastStartedTime: Long = 0L, // Время последнего запуска в миллисекундах
    val delayTime: Long = 0L
)