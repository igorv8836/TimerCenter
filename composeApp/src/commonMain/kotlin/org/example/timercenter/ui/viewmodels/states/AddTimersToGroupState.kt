package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerGroupEntity
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.toGroupType
import org.example.timercenter.ui.model.toInt

@Stable
data class AddTimersToGroupState(
    val timerGroups: List<TimerGroupUiModel> = emptyList(),
    val timerChooseId: Int? = null,
)

sealed interface AddTimersToGroupEffect {
    data class NavigateToEditTimerGroup(val timerGroupId: Int) : AddTimersToGroupEffect
}



sealed interface AddTimersToGroupEvent {
    data class ChooseGroupToEdit(val groupId: Int) : AddTimersToGroupEvent
    data class SetTimerChooseId(val groupId: Int) : AddTimersToGroupEvent

//    data class SetTimerGroupId(val id: Int?) : CreateTimerGroupEvent
//    data class SetName(val text: String) : CreateTimerGroupEvent
//    data class SetGroupType(val groupType: GroupType) : CreateTimerGroupEvent
//    data class SetDelayHours(val value: Int) : CreateTimerGroupEvent
//    data class SetDelayMinutes(val value: Int) : CreateTimerGroupEvent
//    data class SetDelaySeconds(val value: Int) : CreateTimerGroupEvent
//    data class SetShowPopup(val value: Boolean) : CreateTimerGroupEvent
//    data object SaveTimerGroup : CreateTimerGroupEvent
//    data class AddTimerToGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
//    data class DeleteTimerFromGroup(val timer: TimerUiModel) : CreateTimerGroupEvent
}