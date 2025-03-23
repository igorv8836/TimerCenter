package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable


enum class GroupType {
    CONSISTENT,
    PARALLEL,
    DELAY
}

fun GroupType.toInt() : Int {
    return when (this) {
        GroupType.CONSISTENT -> 0
        GroupType.PARALLEL -> 1
        GroupType.DELAY -> 2
    }
}

fun Int.toGroupType() : GroupType {
    return when (this) {
        0 -> GroupType.CONSISTENT
        1 -> GroupType.PARALLEL
        2 -> GroupType.DELAY
        else -> GroupType.CONSISTENT
    }
}

@Stable
data class TimerGroupUiModel(
    val id: Int = -1,
    val groupName: String = "",
    val groupType: GroupType = GroupType.CONSISTENT,
    val timers: List<TimerUiModel> = emptyList(),
    val lastStartedTime: Long = 0L, // Время последнего запуска в миллисекундах
    val delayTime: Long = 0L
)