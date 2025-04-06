package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerStatus
import kotlinx.datetime.Clock

@Stable
data class TimerUiModel(
    val id: Int = -1,
    val timerName: String = "",
    val totalTime: Long = 60_000L,
    val groupId: Int? = null,
    val isRunning: Boolean = false,
    val lastStartedTime: Long = 0L,
    val remainingMillis: Long = totalTime,
)

fun TimerEntity.toUiModel(): TimerUiModel {
    val currentTime = Clock.System.now().toEpochMilliseconds()

    return TimerUiModel(
        id = id,
        timerName = name,
        totalTime = durationMillis,
        isRunning = isRunning,
        lastStartedTime = startTime ?: 0L,
        remainingMillis = remainingMillis,
//        remainingMillis = when (status) {
//            TimerStatus.PAUSED -> remainingMillis
//            TimerStatus.RUNNING -> {
//                if (startTime != null) {
//                    val elapsed = currentTime - (startTime ?: 0L)
//                    maxOf(0, durationMillis - elapsed)
//                } else {
//                    remainingMillis
//                }
//            }
//            else -> durationMillis
//        }
    )
}