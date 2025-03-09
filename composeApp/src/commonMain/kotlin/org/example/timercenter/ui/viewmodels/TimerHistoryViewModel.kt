package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerHistoryRepository
import org.example.timercenter.ui.viewmodels.states.TimerHistoryEvent
import org.example.timercenter.ui.viewmodels.states.TimerHistorySideEffect
import org.example.timercenter.ui.viewmodels.states.TimerHistoryState
import org.orbitmvi.orbit.ContainerHost

class TimerHistoryViewModel(
    private val timerHistoryRepository: TimerHistoryRepository
) : ViewModel(), ContainerHost<TimerHistoryState, TimerHistorySideEffect> {

    override val container = container<TimerHistoryState, TimerHistorySideEffect>(TimerHistoryState())

    fun onEvent(event: TimerHistoryEvent) = intent {
        when (event) {
            is TimerHistoryEvent.LoadHistory -> {
                reduce { state.copy(isLoading = true) }
                timerHistoryRepository.getAllHistory().collect { history ->
                    reduce { state.copy(history = history, isLoading = false) }
                }
            }
            is TimerHistoryEvent.LoadHistoryForTimer -> {
                reduce { state.copy(isLoading = true) }
                timerHistoryRepository.getHistoryForTimer(event.timerId).collect { history ->
                    reduce { state.copy(history = history, isLoading = false) }
                }
            }
            is TimerHistoryEvent.ClearHistory -> {
                try {
                    timerHistoryRepository.clearAll()
                } catch (e: Exception) {
                    postSideEffect(TimerHistorySideEffect.ShowToast("Ошибка очистки истории"))
                }
            }
        }
    }
}