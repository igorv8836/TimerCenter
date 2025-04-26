package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Представляет сущность таймера в базе данных
 * @property id Уникальный идентификатор таймера
 * @property name Отображаемое имя таймера
 * @property durationMillis Общая длительность таймера в миллисекундах
 * @property remainingMillis Остаток времени таймера в миллисекундах
 * @property isRunning Состояние таймера (выполняется или нет)
 * @property startTime Время начала таймера в миллисекундах
 * @property status Состояние таймера
 * @property currentRunId Идентификатор текущего запуска таймера
 */
@Entity(tableName = "timers")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val durationMillis: Long,
    val remainingMillis: Long = durationMillis,
    val isRunning: Boolean = false,
    val startTime: Long? = null,
    val status: TimerStatus = TimerStatus.NOT_STARTED,
    val currentRunId: Int? = null // для группы
)

/**
 * Представляет возможные состояния таймера
 */
enum class TimerStatus {
    RUNNING,
    PAUSED,
    NOT_STARTED,
    COMPLETED,
}