package org.example.timercenter.ui.viewmodels.states

import com.example.timercenter.database.model.TimerEntity

data class TimerDetailState(
    val timer: TimerEntity? = null,
    val isSaving: Boolean = false,
    val error: String? = null
)

sealed class TimerDetailSideEffect {
    data class ShowToast(val message: String) : TimerDetailSideEffect()
    object NavigateBack : TimerDetailSideEffect()
}

sealed class TimerDetailEvent {
    data class LoadTimer(val timerId: Int) : TimerDetailEvent()
    data class SaveTimer(val timer: TimerEntity) : TimerDetailEvent()
    object DeleteTimer : TimerDetailEvent()
}