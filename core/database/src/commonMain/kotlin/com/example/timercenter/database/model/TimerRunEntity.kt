package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Представляет запись запуска таймера
 * @property id Уникальный идентификатор записи
 * @property timerId Идентификатор таймера
 * @property groupId Идентификатор группы (если запуск через группу)
 * @property startTime Время старта в миллисекундах
 * @property remainingMillis Оставшееся время при старте
 * @property isRunning Флаг, показывающий, что запись активна
 * @property status Статус запуска
 */
@Entity(
    tableName = "timer_runs",
    foreignKeys = [
        ForeignKey(
            entity = TimerEntity::class,
            parentColumns = ["id"],
            childColumns = ["timerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TimerGroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimerRunEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timerId: Int,
    val groupId: Int,
    val startTime: Long,
    val remainingMillis: Long,
    val isRunning: Boolean,
    val status: TimerStatus
) 