package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.viewmodels.states.TimerListEvent
import org.example.timercenter.ui.viewmodels.states.TimerListSideEffect
import org.example.timercenter.ui.viewmodels.states.TimerListState
import org.orbitmvi.orbit.ContainerHost

class TimerListViewModel(
    private val timerRepository: TimerRepository
) : ViewModel(), ContainerHost<TimerListState, TimerListSideEffect> {

    override val container = container<TimerListState, TimerListSideEffect>(TimerListState())

    fun onEvent(event: TimerListEvent) = intent {
        when (event) {
            is TimerListEvent.LoadTimers -> {
                reduce { state.copy(isLoading = true) }
                timerRepository.getAllTimers().collect { timers ->
                    reduce { state.copy(timers = timers, isLoading = false) }
                }
            }
            is TimerListEvent.StartTimer -> {
                try {
                    timerRepository.startTimer(event.timer)
                } catch (e: Exception) {
                    postSideEffect(TimerListSideEffect.ShowToast("Ошибка запуска таймера"))
                }
            }
            is TimerListEvent.StopTimer -> {
                try {
                    timerRepository.stopTimer(event.timer)
                } catch (e: Exception) {
                    postSideEffect(TimerListSideEffect.ShowToast("Ошибка остановки таймера"))
                }
            }
            is TimerListEvent.DeleteTimer -> {
                try {
                    timerRepository.deleteTimer(event.timerId)
                } catch (e: Exception) {
                    postSideEffect(TimerListSideEffect.ShowToast("Ошибка удаления таймера"))
                }
            }
            is TimerListEvent.CreateTimer -> {
                postSideEffect(TimerListSideEffect.NavigateToTimerDetail(-1))
            }
        }
    }
}