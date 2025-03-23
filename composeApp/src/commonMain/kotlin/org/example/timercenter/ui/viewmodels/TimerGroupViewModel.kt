package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.ui.viewmodels.states.TimerGroupEvent
import org.example.timercenter.ui.viewmodels.states.TimerGroupSideEffect
import org.example.timercenter.ui.viewmodels.states.TimerGroupState
import org.orbitmvi.orbit.ContainerHost

class TimerGroupViewModel(
    private val timerGroupRepository: TimerGroupRepository
) : ViewModel(), ContainerHost<TimerGroupState, TimerGroupSideEffect> {

    override val container = container<TimerGroupState, TimerGroupSideEffect>(TimerGroupState())

    fun onEvent(event: TimerGroupEvent) = intent {
        when (event) {
            is TimerGroupEvent.LoadGroups -> {
                reduce { state.copy(isLoading = true) }
                timerGroupRepository.getAllGroups().collect { groups ->
                    reduce { state.copy(groups = groups, isLoading = false) }
                }
            }
            is TimerGroupEvent.CreateGroup -> {
//                try {
//                    timerGroupRepository.createGroup(event.group)
//                    postSideEffect(TimerGroupSideEffect.NavigateToGroupDetail)
//                } catch (e: Exception) {
//                    postSideEffect(TimerGroupSideEffect.ShowToast("Ошибка создания группы"))
//                }
            }
            is TimerGroupEvent.DeleteGroup -> {
                try {
                    timerGroupRepository.deleteGroup(event.groupId)
                } catch (e: Exception) {
                    postSideEffect(TimerGroupSideEffect.ShowToast("Ошибка удаления группы"))
                }
            }
        }
    }
}