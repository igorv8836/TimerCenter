package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerGroupEntity
import org.example.timercenter.ui.model.TimerGroupUiModel

data class TimerGroupState(
    val groups: List<TimerGroupEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TimerGroupSideEffect {
    data class ShowToast(val message: String) : TimerGroupSideEffect()
    object NavigateToGroupDetail : TimerGroupSideEffect()
}

sealed class TimerGroupEvent {
    object LoadGroups : TimerGroupEvent()
    data class CreateGroup(val group: TimerGroupUiModel) : TimerGroupEvent()
    data class DeleteGroup(val groupId: Int) : TimerGroupEvent()
}