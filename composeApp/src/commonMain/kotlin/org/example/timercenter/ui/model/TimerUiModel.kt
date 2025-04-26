package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerEntity
import kotlinx.datetime.Clock

/**
 * Модель таймера
 * @property id Идентификатор таймера
 * @property timerName Название таймера
 * @property totalTime Общее время в миллисекундах
 * @property groupId Идентификатор группы (если таймер входит в группу)
 * @property isRunning Флаг запущенного состояния
 * @property lastStartedTime Время последнего запуска в миллисекундах
 * @property remainingMillis Оставшееся время в миллисекундах
 */
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

/**
 * Преобразует сущность таймера в модель
 * @return Модель таймера
 */
fun TimerEntity.toUiModel(groupId: Int? = null): TimerUiModel {
    val currentTime = Clock.System.now().toEpochMilliseconds()

    return TimerUiModel(
        id = id,
        groupId = groupId,
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