package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.viewmodels.states.HomeEffect
import org.example.timercenter.ui.viewmodels.states.HomeEvent
import org.example.timercenter.ui.viewmodels.states.HomeState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental

@OptIn(OrbitExperimental::class)
class HomeViewModel(
    private val timerRepository: TimerRepository,
    private val timerGroupRepository: TimerGroupRepository
) : ViewModel(), ContainerHost<HomeState, HomeEffect> {
    override val container = container<HomeState, HomeEffect>(HomeState())

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
            is HomeEvent.SetTimerRestart -> {
                reduce { state.copy(timerRestartId = event.timerId) }
            }
            is HomeEvent.SetTimerGroupRestart -> {
                reduce { state.copy(timerGroupRestartId = event.timerGroupId) }
            }
            HomeEvent.NavigateToSettingsEvent -> {
                postSideEffect(HomeEffect.NavigateToSettings)
            }
        }
    }
}

fun TimerEntity.toUiModel(): TimerUiModel {
    return TimerUiModel(
        id = id,
        timerName = name,
        totalTime = durationMillis,
        lastStartedTime = startTime ?: 0L
    )
}

fun TimerGroupEntity.toUiModel(): TimerGroupUiModel {
    return TimerGroupUiModel(
        id = id,
        groupName = title,
        groupType = org.example.timercenter.ui.model.GroupType.CONSISTENT,
        timers = emptyList(),
        lastStartedTime = 0L,
        delayTime = 0L
    )
}
