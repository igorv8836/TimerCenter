package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbit_mvi.viewmodel.container
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.model.toUiModel
import org.example.timercenter.ui.viewmodels.states.HomeEffect
import org.example.timercenter.ui.viewmodels.states.HomeEvent
import org.example.timercenter.ui.viewmodels.states.HomeState
import org.orbitmvi.orbit.ContainerHost

class HomeViewModel(
    private val timerRepository: TimerRepository,
    private val timerGroupRepository: TimerGroupRepository
) : ViewModel(), ContainerHost<HomeState, HomeEffect> {
    override val container = container<HomeState, HomeEffect>(HomeState())

    init {
        viewModelScope.launch {
            timerRepository.getAllTimers().collect { timers ->
                intent { reduce { state.copy(timers = timers.map { it.toUiModel() }) } }
            }
        }

        viewModelScope.launch {
            timerGroupRepository.getAllGroups().collect { groups ->
                val newGroups = groups.map { group ->
                    val timers = timerGroupRepository.getTimersInGroup(group.id).first().map {
                        it.toUiModel()
                    }

                    group.toUiModel().copy(
                        timers = timers,
                    )
                }

                intent { reduce { state.copy(timerGroups = newGroups) } }
            }
        }
    }


    fun onEvent(event: HomeEvent) = intent {
        when (event) {
            is HomeEvent.LoadData -> Unit
            is HomeEvent.ToggleTimerSelection -> {
                val isSelected = state.selectedTimers.contains(event.timer)
                val newSelection = if (isSelected) {
                    state.selectedTimers - event.timer
                } else {
                    if (event.isLongPress || state.selectedTimers.isNotEmpty())
                        state.selectedTimers + event.timer
                    else
                        setOf(event.timer)
                }
                reduce { state.copy(selectedTimers = newSelection) }
            }

            is HomeEvent.ToggleTimerGroupSelection -> {
                val isSelected = state.selectedTimerGroups.contains(event.timerGroup)
                val newSelection = if (isSelected) {
                    state.selectedTimerGroups - event.timerGroup
                } else {
                    if (event.isLongPress || state.selectedTimerGroups.isNotEmpty())
                        state.selectedTimerGroups + event.timerGroup
                    else
                        setOf(event.timerGroup)
                }
                reduce { state.copy(selectedTimerGroups = newSelection) }
            }

            HomeEvent.ClearSelection -> {
                reduce { state.copy(selectedTimers = emptySet(), selectedTimerGroups = emptySet()) }
            }

            HomeEvent.ShowDeleteConfirmation -> {
                reduce { state.copy(showDeleteConfirmation = true) }
            }

            HomeEvent.ConfirmDeletion -> {
                state.selectedTimers.forEach { timer ->
                    intent { timerRepository.deleteTimer(timer.id) }
                }
                state.selectedTimerGroups.forEach { group ->
                    intent { timerGroupRepository.deleteGroup(group.id) }
                }
                reduce {
                    state.copy(
                        selectedTimers = emptySet(),
                        selectedTimerGroups = emptySet(),
                        showDeleteConfirmation = false
                    )
                }
            }

            HomeEvent.CancelDeletion -> {
                reduce { state.copy(showDeleteConfirmation = false) }
            }

            HomeEvent.EditSelected -> {
                if (state.selectedTimers.size == 1 && state.selectedTimerGroups.isEmpty()) {
                    val timer = state.selectedTimers.first()
                    postSideEffect(HomeEffect.NavigateToEditTimer(timer.id))
                } else if (state.selectedTimerGroups.size == 1 && state.selectedTimers.isEmpty()) {
                    val group = state.selectedTimerGroups.first()
                    postSideEffect(HomeEffect.NavigateToEditTimerGroup(group.id))
                }
            }

            is HomeEvent.CreateTimerFromHistory -> {
               timerRepository.copyTimer(event.timerId)
            }

            is HomeEvent.CreateTimerGroupFromHistory -> {
                reduce { state.copy(timerGroupRestartId = event.timerGroupId) }
            }

            is HomeEvent.UpdateTimerLastStartedTime -> {

                val timers =
                    state.timers.map { if (it.id == event.timerId) it.copy(lastStartedTime = event.lastStartedTime) else it }
                if (timerRepository.getTimer(event.timerId) != null) {
                    timerRepository.updateTimer(
                        timerRepository.getTimer(event.timerId)!!
                            .copy(startTime = event.lastStartedTime)
                    )
                }
                reduce { state.copy(timers = timers) }
            }

            is HomeEvent.UpdateTimerGroupLastStartedTime -> {
                val timerGroups =
                    state.timerGroups.map { if (it.id == event.timerGroupId) it.copy(lastStartedTime = event.lastStartedTime) else it }
                if (timerGroupRepository.getGroup(event.timerGroupId) != null) {
                    timerGroupRepository.updateGroup(
                        timerGroupRepository.getGroup(event.timerGroupId)!!
                            .copy(lastStartedTime = event.lastStartedTime),
                        timerIds = timerGroups.map { it.id }
                    )
                }
                reduce { state.copy(timerGroups = timerGroups) }
            }

            is HomeEvent.RunTimer -> {
                timerRepository.startTimer(event.timerId)
            }

            is HomeEvent.StopTimer -> {
                timerRepository.stopTimer(event.timerId)
            }

            is HomeEvent.PauseTimer -> {
                timerRepository.pauseTimer(event.timerId)
            }
        }
    }
}



