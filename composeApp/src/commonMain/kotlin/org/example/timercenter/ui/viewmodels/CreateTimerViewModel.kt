package org.example.timercenter.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import org.example.timercenter.domain.repositories.TimerRepository
import org.example.timercenter.ui.viewmodels.states.CreateTimerEffect
import org.example.timercenter.ui.viewmodels.states.CreateTimerEvent
import org.example.timercenter.ui.viewmodels.states.CreateTimerState
import org.example.timercenter.ui.viewmodels.states.toEntity
import org.orbitmvi.orbit.ContainerHost

class CreateTimerViewModel(
    private val timerRepository: TimerRepository
) : ViewModel(), ContainerHost<CreateTimerState, CreateTimerEffect> {
    override val container = container<CreateTimerState, CreateTimerEffect>(CreateTimerState())

    fun onEvent(event: CreateTimerEvent) {
        when (event) {
            is CreateTimerEvent.SaveTimer -> blockingIntent {
                val timerEntity = state.toEntity()
                val newId = timerRepository.createTimer(timerEntity)
                reduce {
                    state.copy(
                        id = newId,
                        timerInfo = state.timerInfo.copy(id = newId)
                    )
                }
                postSideEffect(CreateTimerEffect.NavigateToHome)
            }
            is CreateTimerEvent.SetHours -> blockingIntent { reduce { state.copy(selectedHours = event.value) } }
            is CreateTimerEvent.SetMinutes -> blockingIntent { reduce { state.copy(selectedMinutes = event.value) } }
            is CreateTimerEvent.SetName -> blockingIntent {
                reduce {
                    state.copy(
                        timerInfo = state.timerInfo.copy(timerName = event.text)
                    )
                }
            }
            is CreateTimerEvent.SetSeconds -> blockingIntent { reduce { state.copy(selectedSeconds = event.value) } }
            is CreateTimerEvent.SetShowPopup -> intent { reduce { state.copy(showPopup = event.value) } }
            is CreateTimerEvent.SetStartImmediately -> blockingIntent {
                reduce { state.copy(startImmediately = event.value) }
            }
            is CreateTimerEvent.SetTimerId -> blockingIntent {
                if (event.id == null) {
                    reduce { state.copy(id = null) }
                } else {
                    val timer = timerRepository.getTimer(event.id)
                    timer?.let {
                        reduce {
                            state.copy(
                                id = it.id,
                                timerInfo = state.timerInfo.copy(
                                    id = it.id,
                                    timerName = it.name,
                                    totalTime = it.durationMillis,
                                    lastStartedTime = it.startTime ?: 0L
                                ),
                                selectedHours = (it.durationMillis / 3_600_000L).toInt(),
                                selectedMinutes = (((it.durationMillis % 3_600_000L) / 60_000L)).toInt(),
                                selectedSeconds = (((it.durationMillis % 60_000L) / 1_000L).toInt())
                            )
                        }
                    }
                }
            }
        }
    }
}
