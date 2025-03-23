package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.model.toUiModel
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEffect
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEvent
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupState
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupEffect
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupEvent
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupState
import org.example.timercenter.ui.viewmodels.states.toEntity
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental

@OptIn(OrbitExperimental::class)
class AddTimersToGroupViewModel(
    private val timerGroupRepository: TimerGroupRepository,
) : ViewModel(), ContainerHost<AddTimersToGroupState, AddTimersToGroupEffect> {
    override val container = container<AddTimersToGroupState, AddTimersToGroupEffect>(
        AddTimersToGroupState()
    )

    init {
        intent {
            subIntent {
                timerGroupRepository.getAllGroups().collect { timerGroupEntities ->
                    val groups = timerGroupEntities.map { it.toUiModel() }
                    reduce { state.copy(timerGroups = groups) }

                }
            }
        }
    }

    fun onEvent(event: AddTimersToGroupEvent) {
        when(event) {
            is AddTimersToGroupEvent.ChooseGroupToEdit -> blockingIntent {
                postSideEffect(AddTimersToGroupEffect.NavigateToEditTimerGroup(timerGroupId = event.groupId))
            }
            is AddTimersToGroupEvent.SetTimerChooseId -> blockingIntent {
                reduce { state.copy(timerChooseId = event.groupId) }
            }
        }

    }
}
