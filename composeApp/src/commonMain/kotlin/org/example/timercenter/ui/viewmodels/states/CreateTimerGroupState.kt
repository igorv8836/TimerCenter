package org.example.timercenter.ui.viewmodels.states


import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.toGroupType
import org.example.timercenter.ui.model.toInt

@Stable
data class CreateTimerGroupState(
    val id: Int? = null,
    val timerGroupInfo: TimerGroupUiModel = TimerGroupUiModel(),
    val addedTimers: List<TimerUiModel> = emptyList(),
    val allTimers: List<TimerUiModel> = emptyList(),
    val delaySelectedHours: Int = 0,
    val delaySelectedMinutes: Int = 0,
    val delaySelectedSeconds: Int = 0,
    val showPopup: Boolean = false
)

sealed interface CreateTimerGroupEffect {
    data object NavigateToHome : CreateTimerGroupEffect
}

fun CreateTimerGroupState.getMilliseconds(): Long {
    return (delaySelectedHours * 3_600_000L) + (delaySelectedMinutes * 60_000L) + (delaySelectedSeconds * 1_000L)
}


fun CreateTimerGroupState.toEntity() : TimerGroupEntity {
    return TimerGroupEntity(
        id = id ?: 0,
        name = timerGroupInfo.groupName,
        groupType = timerGroupInfo.groupType.toInt(),
        isRunning = false,
        lastStartedTime = timerGroupInfo.lastStartedTime,
        delayTime = timerGroupInfo.delayTime
    )
}

sealed interface CreateTimerGroupEvent {
    data class SetTimerGroupId(val id: Int?) : CreateTimerGroupEvent
    data class SetName(val text: String) : CreateTimerGroupEvent
    data class SetGroupType(val groupType: GroupType) : CreateTimerGroupEvent
    data class SetDelayHours(val value: Int) : CreateTimerGroupEvent
    data class SetDelayMinutes(val value: Int) : CreateTimerGroupEvent
    data class SetDelaySeconds(val value: Int) : CreateTimerGroupEvent
    data class SetShowPopup(val value: Boolean) : CreateTimerGroupEvent

    data object SaveTimerGroup : CreateTimerGroupEvent

    data class AddTimerToGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
    data class DeleteTimerFromGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
}