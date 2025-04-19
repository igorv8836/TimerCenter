package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Представляет сущность таймера в базе данных
 * @property id Уникальный идентификатор таймера
 * @property name Отображаемое имя таймера
 * @property durationMillis Общая длительность таймера в миллисекундах
 * @property remainingMillis Оставшееся время в миллисекундах
 * @property isRunning Флаг, указывающий, запущен ли таймер в данный момент
 * @property startTime Временная метка начала таймера, null если не запущен
 * @property status Текущий статус таймера
 */
@Entity(tableName = "timers")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val durationMillis: Long,
    val remainingMillis: Long = durationMillis,
    val isRunning: Boolean = false,
    val startTime: Long? = null,
    val status: TimerStatus = TimerStatus.NOT_STARTED
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