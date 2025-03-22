package org.example.timercenter.ui.viewmodels.states

import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel

//data class TimerHistoryState(
//    val history: List<TimerHistoryEntity> = emptyList(),
//    val isLoading: Boolean = false,
//    val error: String? = null
//)

//sealed class TimerHistorySideEffect {
//    data class ShowToast(val message: String) : TimerHistorySideEffect()
//    data class NavigateToHomeRestartTimer(val timerId: Int) : TimerHistorySideEffect
//    data class NavigateToHomeRestartTimerGroup(val timerGroupId: Int) : TimerHistorySideEffect
//}

data class TimerHistoryState(
    val timers: List<TimerUiModel> = emptyList(),
    val timerGroups: List<TimerGroupUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface TimerHistorySideEffect {
    data class ShowToast(val message: String) : TimerHistorySideEffect
    data class NavigateToHomeRestartTimer(val timerId: Int) : TimerHistorySideEffect
    data class NavigateToHomeRestartTimerGroup(val timerGroupId: Int) : TimerHistorySideEffect
}

sealed interface TimerHistoryEvent {
    object LoadHistory : TimerHistoryEvent
    data class LoadHistoryForTimer(val timerId: Int) : TimerHistoryEvent
    object ClearHistory : TimerHistoryEvent
    object RestartSelected: TimerHistoryEvent
    data class SetTimerRestart(val timerId: Int) : TimerHistoryEvent
    data class SetTimerGroupRestart(val timerGroupId: Int) : TimerHistoryEvent
    data class NavigateToHomeRestartTimerEvent(val timerId: Int) : TimerHistoryEvent
    data class NavigateToHomeRestartTimerGroupEvent(val timerGroupId: Int) : TimerHistoryEvent

}
//
//fun TimerHistoryEntity.toUiModel(): TimerHistoryUiModel {
//    return TimerHistoryUiModel(
//        id = id,
//        name =
//    )
//}