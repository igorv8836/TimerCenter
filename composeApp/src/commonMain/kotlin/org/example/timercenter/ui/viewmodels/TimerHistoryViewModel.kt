package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbit_mvi.viewmodel.container
import kotlinx.coroutines.launch
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.model.toUiModel
import org.example.timercenter.ui.viewmodels.states.TimerHistoryEvent
import org.example.timercenter.ui.viewmodels.states.TimerHistorySideEffect
import org.example.timercenter.ui.viewmodels.states.TimerHistoryState
import org.orbitmvi.orbit.ContainerHost

/**
 * ViewModel для экрана истории таймеров
 * Реализует логику отображения истории таймеров и групп таймеров
 * @property timerRepository Репозиторий для работы с таймерами
 * @property timerGroupRepository Репозиторий для работы с группами таймеров
 */
class TimerHistoryViewModel(
    private val timerRepository: TimerRepository,
    private val timerGroupRepository: TimerGroupRepository
) : ViewModel(), ContainerHost<TimerHistoryState, TimerHistorySideEffect> {

    override val container =
        container<TimerHistoryState, TimerHistorySideEffect>(TimerHistoryState())

    init {
        intent {
            viewModelScope.launch {
                timerRepository.getAllTimers().collect { timerEntities ->
                    val timers = timerEntities.map { it.toUiModel() }
                    reduce { state.copy(timers = timers) }
                }
            }
            viewModelScope.launch {
                timerGroupRepository.getAllGroups().collect { groupEntities ->
                    val groups = groupEntities.map { it.toUiModel() }
                    reduce { state.copy(timerGroups = groups) }
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