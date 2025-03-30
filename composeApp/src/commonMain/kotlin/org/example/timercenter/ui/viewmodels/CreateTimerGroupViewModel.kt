package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import kotlinx.coroutines.flow.first
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.model.toGroupType
import org.example.timercenter.ui.model.toUiModel
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupEffect
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupEvent
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupState
import org.example.timercenter.ui.viewmodels.states.toEntity
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental

@OptIn(OrbitExperimental::class)
class CreateTimerGroupViewModel(
    private val timerGroupRepository: TimerGroupRepository,
    private val timerRepository: TimerRepository
) : ViewModel(), ContainerHost<CreateTimerGroupState, CreateTimerGroupEffect> {
    override val container =
        container<CreateTimerGroupState, CreateTimerGroupEffect>(CreateTimerGroupState())

    init {
        intent {
            subIntent {
                timerRepository.getAllTimers().collect { timerEntities ->
                    val timers = timerEntities.map { it.toUiModel() }
                    reduce { state.copy(allTimers = timers) }
                }
            }
        }
    }

    fun onEvent(event: CreateTimerGroupEvent) {
        when (event) {
            is CreateTimerGroupEvent.SaveTimerGroup -> blockingIntent {
                val timerGroupEntity = state.toEntity()
                val newId = timerGroupRepository.createGroup(
                    timerGroupEntity,
                    timerIds = state.allTimers.mapNotNull { it.id },
                )
                state.timerGroupInfo.timers.forEach { timerUiModel ->
                    timerRepository.updateTimerInGroupId(timerId = timerUiModel.id, groupId = newId)
                }
                val notAddedTimers = state.allTimers - state.timerGroupInfo.timers.toSet()
                notAddedTimers.forEach { timerUiModel ->
                    timerRepository.resetTimerInGroupId(timerId = timerUiModel.id, groupId = newId)
                }

                reduce {
                    state.copy(
                        id = newId,
                        timerGroupInfo = state.timerGroupInfo.copy(id = newId)
                    )
                }
                postSideEffect(CreateTimerGroupEffect.NavigateToHome)
            }

            is CreateTimerGroupEvent.SetTimerGroupId -> blockingIntent {
                if (event.id == null) {
                    reduce { state.copy(id = null) }
                } else {
                    val timerGroup = timerGroupRepository.getGroup(event.id)
                    timerGroup?.let {
                        val timersInGroup = timerGroupRepository.getTimersInGroup(it.id).first()
                        // Преобразуем в UI-модель
                        val timersUi = timersInGroup.map { entity -> entity.toUiModel() }

                        reduce {
                            state.copy(
                                id = it.id,
                                timerGroupInfo = state.timerGroupInfo.copy(
                                    id = it.id,
                                    groupName = it.name,
                                    groupType = it.groupType.toGroupType(),
                                    delayTime = it.delayTime,
                                    timers = timersUi,
                                    lastStartedTime = it.lastStartedTime ?: 0L
                                ),
                                delaySelectedHours = (it.delayTime / 3_600_000L).toInt(),
                                delaySelectedMinutes = ((it.delayTime % 3_600_000L) / 60_000L).toInt(),
                                delaySelectedSeconds = ((it.delayTime % 60_000L) / 1_000L).toInt()
                            )
                        }
                    }
                }
            }

            is CreateTimerGroupEvent.AddTimerToGroup -> blockingIntent {
                reduce {
                    state.copy(timerGroupInfo = state.timerGroupInfo.copy(timers = state.timerGroupInfo.timers + event.timer))
                }
            }

            is CreateTimerGroupEvent.DeleteTimerFromGroup -> blockingIntent {
                reduce {
                    state.copy(timerGroupInfo = state.timerGroupInfo.copy(timers = state.timerGroupInfo.timers - event.timer))
                }
            }

            is CreateTimerGroupEvent.SetDelayHours -> blockingIntent {
                reduce {
                    state.copy(
                        delaySelectedHours = event.value
                    )
                }
            }

            is CreateTimerGroupEvent.SetDelayMinutes -> blockingIntent {
                reduce {
                    state.copy(
                        delaySelectedMinutes = event.value
                    )
                }
            }

            is CreateTimerGroupEvent.SetName -> blockingIntent {
                reduce {
                    state.copy(
                        timerGroupInfo = state.timerGroupInfo.copy(groupName = event.text)
                    )
                }
            }

            is CreateTimerGroupEvent.SetDelaySeconds -> blockingIntent {
                reduce {
                    state.copy(
                        delaySelectedSeconds = event.value
                    )
                }
            }

            is CreateTimerGroupEvent.SetShowPopup -> intent { reduce { state.copy(showPopup = event.value) } }

            is CreateTimerGroupEvent.SetGroupType -> blockingIntent {
                reduce {
                    state.copy(
                        timerGroupInfo = state.timerGroupInfo.copy(groupType = event.groupType)
                    )
                }
            }
        }
    }
}
