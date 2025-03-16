package org.example.timercenter.ui.model


enum class GroupType {
    CONSISTENT,
    PARALLEL,
    DELAY
}

data class TimerGroupUiModel(
    val id: Int,
    val groupName: String,
    val groupType: GroupType,
    val timers: List<TimerUiModel>,
    val lastStartedTime: Long = 0L // Время последнего запуска в миллисекундах
)