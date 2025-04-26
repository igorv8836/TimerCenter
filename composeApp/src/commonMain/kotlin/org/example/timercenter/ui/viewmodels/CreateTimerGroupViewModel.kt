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

/**
 * ViewModel для создания и редактирования группы таймеров
 * Класс отвечает за управление состоянием и логикой экрана создания и редактирования группы таймеров
 * @property timerGroupRepository Репозиторий для работы с группами таймеров
 * @property timerRepository Репозиторий для работы с таймерами
 */
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
                    reduce { state.copy(
                        allTimers = timers,
                        unSelectedTimers = timers,
                    ) }
                }
            }
        }
    }

    /**
     * Обработчик событий для создания/редактирования группы таймеров
     * Обрабатывает события, связанные с созданием и редактированием группы таймеров
     * @param event Событие для обработки
     */
    fun onEvent(event: CreateTimerGroupEvent) {
        when (event) {
            is CreateTimerGroupEvent.SaveTimerGroup -> blockingIntent {
                if (state.timerGroupInfo.groupName.isBlank()) {
                    reduce { state.copy(errorMessage = "Имя группы не может быть пустым") }
                    return@blockingIntent
                }
                if (state.timerGroupInfo.timers.isEmpty()) {
                    reduce { state.copy(errorMessage = "В группе должен быть хотя бы один таймер") }
                    return@blockingIntent
                }
                if (state.id != null) {
                    if (!state.showPopup) {
                        reduce { state.copy(showPopup = true) }
                        return@blockingIntent
                    }
                    val entity = state.toEntity()
                    timerGroupRepository.updateGroup(
                        entity,
                        timerIds = state.timerGroupInfo.timers.map { it.id }
                    )
                    reduce {
                        state.copy(
                            showPopup = false
                        )
                    }
                    postSideEffect(CreateTimerGroupEffect.NavigateToHome)
                    return@blockingIntent
                }
                // Create new group
                // Clear any previous error
                reduce { state.copy(errorMessage = null) }
                val timerGroupEntity = state.toEntity()
                val newId = timerGroupRepository.createGroup(
                    timerGroupEntity,
                    timerIds = state.allTimers.map { it.id },
                )

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

                        reduce {
                            state.copy(
                                id = it.id,
                                timerGroupInfo = state.timerGroupInfo.copy(
                                    id = it.id,
                                    groupName = it.name,
                                    groupType = it.groupType.toGroupType(),
                                    delayTime = it.delayTime,
                                    timers = timersInGroup,
                                    lastStartedTime = it.lastStartedTime ?: 0L
                                ),
                                unSelectedTimers = state.allTimers.filter { timer ->
                                    !timersInGroup.map { timerInGroup -> timerInGroup.id }.contains(timer.id)
                                },
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
                    val selectedTimers = state.timerGroupInfo.timers + event.timer
                    state.copy(
                        timerGroupInfo = state.timerGroupInfo.copy(timers = selectedTimers),
                        unSelectedTimers = state.allTimers.filter { timer ->
                            !selectedTimers.map { timerInGroup -> timerInGroup.id }.contains(timer.id)
                        },
                        errorMessage = null
                    )
                }
            }

            is CreateTimerGroupEvent.DeleteTimerFromGroup -> blockingIntent {
                reduce {
                    val selectedTimers = state.timerGroupInfo.timers - event.timer

                    state.copy(
                        timerGroupInfo = state.timerGroupInfo.copy(timers = selectedTimers),
                        unSelectedTimers = state.allTimers.filter { timer ->
                            !selectedTimers.map { timerInGroup -> timerInGroup.id }.contains(timer.id)
                        },
                        errorMessage = null
                    )
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
                        timerGroupInfo = state.timerGroupInfo.copy(groupName = event.text),
                        errorMessage = null
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
