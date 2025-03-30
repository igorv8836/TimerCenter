package org.example.timercenter.ui.viewmodels.states

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerEntity
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.di.KoinFactory
import org.example.timercenter.ui.model.TimerUiModel

@Stable
data class CreateTimerState(
    val id: Int? = null,
    val timerInfo: TimerUiModel = TimerUiModel(),
    val selectedHours: Int = 0,
    val selectedMinutes: Int = 0,
    val selectedSeconds: Int = 0,
    val startImmediately: Boolean = false,
    val showPopup: Boolean = false
)

sealed interface CreateTimerEffect {
    data object NavigateToHome : CreateTimerEffect
}

fun CreateTimerState.getMilliseconds(): Long {
    return (selectedHours * 3_600_000L) + (selectedMinutes * 60_000L) + (selectedSeconds * 1_000L)
}

fun CreateTimerState.toEntity(): TimerEntity {
    return TimerEntity(
        id = id ?: 0,
        name = timerInfo.timerName,
        durationMillis = getMilliseconds(),
        isRunning = startImmediately,
        startTime = KoinFactory.getDI().get<TimeAgoManager>().currentTimeMillis(),
    )
}

fun TimerEntity.toUiModel(): TimerUiModel {
    return TimerUiModel(
        id = id,
        timerName = name,
        totalTime = durationMillis,
        lastStartedTime = startTime ?: 0L
    )
}

sealed interface CreateTimerEvent {
    data class SetTimerId(val id: Int?) : CreateTimerEvent
    data class SetName(val text: String) : CreateTimerEvent
    data class SetHours(val value: Int) : CreateTimerEvent
    data class SetMinutes(val value: Int) : CreateTimerEvent
    data class SetSeconds(val value: Int) : CreateTimerEvent
    data class SetStartImmediately(val value: Boolean) : CreateTimerEvent
    data class SetShowPopup(val value: Boolean) : CreateTimerEvent
    data object SaveTimer : CreateTimerEvent
}