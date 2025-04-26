package org.example.timercenter.ui.model

import androidx.compose.runtime.Stable
import com.example.timercenter.database.model.TimerGroupEntity

/**
 * Перечисление типов групп таймеров
 */
enum class GroupType {
    CONSISTENT,
    PARALLEL,
    DELAY
}

/**
 * Преобразует тип группы в целое число
 * @return Числовое значение типа группы
 */
fun GroupType.toInt() : Int {
    return when (this) {
        GroupType.CONSISTENT -> 0
        GroupType.PARALLEL -> 1
        GroupType.DELAY -> 2
    }
}

/**
 * Преобразует целое число в тип группы
 * @return Тип группы или CONSISTENT по умолчанию
 */
fun Int.toGroupType() : GroupType {
    return when (this) {
        0 -> GroupType.CONSISTENT
        1 -> GroupType.PARALLEL
        2 -> GroupType.DELAY
        else -> GroupType.CONSISTENT
    }
}

/**
 * Модель группы таймеров
 * @property id Идентификатор группы
 * @property groupName Название группы
 * @property groupType Тип группы
 * @property timers Список таймеров в группе
 * @property lastStartedTime Время последнего запуска в миллисекундах
 * @property delayTime Время задержки между таймерами в миллисекундах
 */
@Stable
data class TimerGroupUiModel(
    val id: Int = -1,
    val groupName: String = "",
    val groupType: GroupType = GroupType.CONSISTENT,
    val timers: List<TimerUiModel> = emptyList(),
    val isRunning: Boolean = false,
    val isStarted: Boolean = false,
    val lastStartedTime: Long = 0L,
    val delayTime: Long = 0L
)

/**
 * Преобразует сущность группы таймеров в модель с указанным списком таймеров
 * @param timers Список таймеров
 * @return Модель группы таймеров
 */
fun TimerGroupEntity.toUiModel(timers: List<TimerUiModel>): TimerGroupUiModel {
    return TimerGroupUiModel(
        id = id,
        groupName = name,
        groupType = groupType.toGroupType(),
        isRunning = isRunning,
        isStarted = timers.any { it.remainingMillis != it.totalTime || it.isRunning } || isRunning,
        timers = timers,
        lastStartedTime = 0L,
        delayTime = 0L
    )
}

/**
 * Преобразует сущность группы таймеров в модель
 * @return Модель группы таймеров
 */
fun TimerGroupEntity.toUiModel(): TimerGroupUiModel {
    return TimerGroupUiModel(
        id = id,
        groupName = name,
        groupType = groupType.toGroupType(),
        timers = emptyList(),
        isStarted = isRunning,
        isRunning = isRunning,
        lastStartedTime = lastStartedTime,
        delayTime = delayTime
    )
}