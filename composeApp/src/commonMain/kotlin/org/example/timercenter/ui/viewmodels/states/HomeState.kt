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
    data class NavigateToEditTimer(val timerId: Int) : HomeEffect
    data class NavigateToEditTimerGroup(val timerGroupId: Int) : HomeEffect
}

sealed interface HomeEvent {
    data object LoadData : HomeEvent
    data class ToggleTimerSelection(val timer: TimerUiModel, val isLongPress: Boolean) : HomeEvent
    data class ToggleTimerGroupSelection(val timerGroup: TimerGroupUiModel, val isLongPress: Boolean) : HomeEvent
    data object ClearSelection : HomeEvent
    data object ShowDeleteConfirmation : HomeEvent
    data object ConfirmDeletion : HomeEvent
    data object CancelDeletion : HomeEvent
    data object EditSelected : HomeEvent
    data class UpdateTimerLastStartedTime(val timerId: Int, val lastStartedTime: Long) : HomeEvent
    data class UpdateTimerGroupLastStartedTime(val timerGroupId: Int, val lastStartedTime: Long) : HomeEvent
    data class CreateTimerFromHistory(val timerId: Int) : HomeEvent
    data class CreateTimerGroupFromHistory(val timerGroupId: Int) : HomeEvent

    data class PauseTimer(val timerId: Int) : HomeEvent
    data class StopTimer(val timerId: Int) : HomeEvent
    data class RunTimer(val timerId: Int) : HomeEvent
}
