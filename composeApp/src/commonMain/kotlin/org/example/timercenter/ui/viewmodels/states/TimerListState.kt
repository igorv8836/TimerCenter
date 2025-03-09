package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerEntity

data class TimerListState(
    val timers: List<TimerEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TimerListSideEffect {
    data class ShowToast(val message: String) : TimerListSideEffect()
    data class NavigateToTimerDetail(val timerId: Int) : TimerListSideEffect()
}

sealed class TimerListEvent {
    object LoadTimers : TimerListEvent()
    data class StartTimer(val timer: TimerEntity) : TimerListEvent()
    data class StopTimer(val timer: TimerEntity) : TimerListEvent()
    data class DeleteTimer(val timerId: Int) : TimerListEvent()
    object CreateTimer : TimerListEvent()
}