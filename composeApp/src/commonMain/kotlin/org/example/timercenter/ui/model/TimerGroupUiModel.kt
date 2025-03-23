package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerGroupEntity


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

fun TimerGroupEntity.toUiModel(timers: List<TimerUiModel>): TimerGroupUiModel {
    return TimerGroupUiModel(
        id = id,
        groupName = name,
        groupType = GroupType.CONSISTENT,
        timers = timers,
        lastStartedTime = 0L,
        delayTime = 0L
    )
}

fun TimerGroupEntity.toUiModel(): TimerGroupUiModel {
    return TimerGroupUiModel(
        id = id,
        groupName = name,
        groupType = groupType.toGroupType(),
        timers = emptyList(),
        lastStartedTime = lastStartedTime,
        delayTime = delayTime
    )
}