package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerHistoryEntity

data class TimerHistoryState(
    val history: List<TimerHistoryEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TimerHistorySideEffect {
    data class ShowToast(val message: String) : TimerHistorySideEffect()
}

sealed class TimerHistoryEvent {
    object LoadHistory : TimerHistoryEvent()
    data class LoadHistoryForTimer(val timerId: Int) : TimerHistoryEvent()
    object ClearHistory : TimerHistoryEvent()
}