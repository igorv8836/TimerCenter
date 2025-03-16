package org.example.timercenter.ui.viewmodels.states

import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel


data class HomeState(
    val timers: List<TimerUiModel> = emptyList(),
    val timerGroups: List<TimerGroupUiModel> = emptyList(),
    val selectedTimers: Set<TimerUiModel> = emptySet(),
    val selectedTimerGroups: Set<TimerGroupUiModel> = emptySet(),
    val timerRestartId: Int? = null,
    val timerGroupRestartId: Int? = null,
    val showDeleteConfirmation: Boolean = false
)

sealed interface HomeEffect {
    object NavigateToSettings : HomeEffect
    data class NavigateToEditTimer(val timerId: Int) : HomeEffect
    data class NavigateToEditTimerGroup(val timerGroupId: Int) : HomeEffect
}

sealed interface HomeEvent {
    object LoadData : HomeEvent
    data class ToggleTimerSelection(val timer: TimerUiModel, val isLongPress: Boolean) : HomeEvent
    data class ToggleTimerGroupSelection(val timerGroup: TimerGroupUiModel, val isLongPress: Boolean) : HomeEvent
    object ClearSelection : HomeEvent
    object ShowDeleteConfirmation : HomeEvent
    object ConfirmDeletion : HomeEvent
    object CancelDeletion : HomeEvent
    object EditSelected : HomeEvent
    data class UpdateTimerLastStartedTime(val timerId: Int, val lastStartedTime: Long) : HomeEvent
    data class UpdateTimerGroupLastStartedTime(val timerGroupId: Int, val lastStartedTime: Long) : HomeEvent
    data class SetTimerRestart(val timerId: Int) : HomeEvent
    data class SetTimerGroupRestart(val timerGroupId: Int) : HomeEvent
    object NavigateToSettingsEvent : HomeEvent
}
