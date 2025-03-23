package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.model.toUiModel
import org.example.timercenter.ui.viewmodels.states.TimerHistoryEvent
import org.example.timercenter.ui.viewmodels.states.TimerHistorySideEffect
import org.example.timercenter.ui.viewmodels.states.TimerHistoryState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental


@OptIn(OrbitExperimental::class)
class TimerHistoryViewModel(
    private val timerRepository: TimerRepository,
    private val timerGroupRepository: TimerGroupRepository
) : ViewModel(), ContainerHost<TimerHistoryState, TimerHistorySideEffect> {

    override val container = container<TimerHistoryState, TimerHistorySideEffect>(TimerHistoryState())



    init {
        intent {
            subIntent {
                timerRepository.getAllTimers().collect { timerEntities ->
                    val timers = timerEntities.map { it.toUiModel() }
                    reduce { state.copy(timers = timers) }
                }
            }
            subIntent {
                timerGroupRepository.getAllGroups().collect { groupEntities ->
                    val groups = groupEntities.map { it.toUiModel() }
                    reduce { state.copy(timerGroups = groups) }
                }
            }
        }
    }

    fun onEvent(event: TimerHistoryEvent) = intent {
        when (event) {
            is TimerHistoryEvent.NavigateToHomeRestartTimerEvent -> {
                postSideEffect(TimerHistorySideEffect.NavigateToHomeRestartTimer(timerId = event.timerId))
            }
            is TimerHistoryEvent.NavigateToHomeRestartTimerGroupEvent -> {
                postSideEffect(TimerHistorySideEffect.NavigateToHomeRestartTimerGroup(timerGroupId = event.timerGroupId))
            }
            else -> {}
        }
    }
}