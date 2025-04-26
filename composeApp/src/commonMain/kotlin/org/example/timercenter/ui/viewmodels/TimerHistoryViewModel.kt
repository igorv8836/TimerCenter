package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbit_mvi.viewmodel.container
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.example.timercenter.domain.repositories.TimerGroupHistoryRepository
import org.example.timercenter.domain.repositories.TimerHistoryRepository
import org.example.timercenter.ui.viewmodels.states.TimerHistoryEvent
import org.example.timercenter.ui.viewmodels.states.TimerHistorySideEffect
import org.example.timercenter.ui.viewmodels.states.TimerHistoryState
import org.orbitmvi.orbit.ContainerHost

/**
 * ViewModel для экрана истории таймеров
 * Реализует логику отображения истории таймеров и групп таймеров
 * @property timerHistoryRepository Репозиторий для работы с таймерами
 * @property timerGroupHistoryRepository Репозиторий для работы с группами таймеров
 */
class TimerHistoryViewModel(
    private val timerHistoryRepository: TimerHistoryRepository,
    private val timerGroupHistoryRepository: TimerGroupHistoryRepository
) : ViewModel(), ContainerHost<TimerHistoryState, TimerHistorySideEffect> {

    override val container =
        container<TimerHistoryState, TimerHistorySideEffect>(TimerHistoryState())

    init {
        viewModelScope.launch {
            combine(
                timerHistoryRepository.getAllHistory(),
                timerGroupHistoryRepository.getAllHistory()
            ) { timerHistories, timerGroupHistories ->
                timerHistories + timerGroupHistories
            }.collect { combinedList ->
                intent {
                    reduce { state.copy(historyItems = combinedList) }
                }
            }
        }
    }

    /**
     * Обработка событий
     * @param event Событие для обработки
     */
    fun onEvent(event: TimerHistoryEvent) = intent {
        when (event) {
            else -> {

            }
        }
    }
}