package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.viewmodels.states.TimerListEvent
import org.example.timercenter.ui.viewmodels.states.TimerListSideEffect
import org.example.timercenter.ui.viewmodels.states.TimerListState
import org.orbitmvi.orbit.ContainerHost

/**
 * ViewModel для экрана списка таймеров
 * Реализует логику управления списком таймеров
 * @property timerRepository Репозиторий для работы с таймерами
 */
class TimerListViewModel(
    private val timerRepository: TimerRepository
) : ViewModel(), ContainerHost<TimerListState, TimerListSideEffect> {

    override val container = container<TimerListState, TimerListSideEffect>(TimerListState())

    /**
     * Обработка событий
     * @param event Событие для обработки
     */
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
                    timerRepository.startTimer(event.timer.id)
                } catch (e: Exception) {
                    postSideEffect(TimerListSideEffect.ShowToast("Ошибка запуска таймера"))
                }
            }
            is TimerListEvent.StopTimer -> {
                try {
                    timerRepository.stopTimer(event.timer.id)
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