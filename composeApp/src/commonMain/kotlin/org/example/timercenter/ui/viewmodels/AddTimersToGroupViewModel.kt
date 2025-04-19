package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.ui.model.toUiModel
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEffect
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEvent
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental

/**
 * ViewModel для экрана добавления таймеров в группу
 * @property timerGroupRepository репозиторий для работы с группами таймеров
 */
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

    /**
     * Обработка событий
     * @param event событие
     */
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
