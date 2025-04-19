package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.viewmodels.states.TimerDetailEvent
import org.example.timercenter.ui.viewmodels.states.TimerDetailSideEffect
import org.example.timercenter.ui.viewmodels.states.TimerDetailState
import org.orbitmvi.orbit.ContainerHost

/**
 * ViewModel для экрана деталей таймера
 * Реализует логику просмотра и редактирования деталей таймера
 * @property timerRepository Репозиторий для работы с таймерами
 */
class TimerDetailViewModel(
    private val timerRepository: TimerRepository
) : ViewModel(), ContainerHost<TimerDetailState, TimerDetailSideEffect> {

    override val container = container<TimerDetailState, TimerDetailSideEffect>(TimerDetailState())

    /**
     * Обработка событий
     * @param event Событие для обработки
     */
    fun onEvent(event: TimerDetailEvent) = intent {
        when (event) {
            is TimerDetailEvent.LoadTimer -> {
                if (event.timerId != -1) {
                    val timer = timerRepository.getTimer(event.timerId)
                    if (timer != null) {
                        reduce { state.copy(timer = timer) }
                    } else {
                        postSideEffect(TimerDetailSideEffect.ShowToast("Таймер не найден"))
                    }
                } else {
                    reduce { state.copy(timer = null) }
                }
            }
            is TimerDetailEvent.SaveTimer -> {
                reduce { state.copy(isSaving = true) }
                try {
                    if (event.timer.id == 0) {
                        timerRepository.createTimer(event.timer)
                    } else {
                        timerRepository.updateTimer(event.timer)
                    }
                    postSideEffect(TimerDetailSideEffect.NavigateBack)
                } catch (e: Exception) {
                    reduce { state.copy(isSaving = false, error = "Ошибка сохранения") }
                }
            }
            is TimerDetailEvent.DeleteTimer -> {
                state.timer?.let { timer ->
                    try {
                        timerRepository.deleteTimer(timer.id)
                        postSideEffect(TimerDetailSideEffect.NavigateBack)
                    } catch (e: Exception) {
                        postSideEffect(TimerDetailSideEffect.ShowToast("Ошибка удаления таймера"))
                    }
                }
            }
        }
    }
}