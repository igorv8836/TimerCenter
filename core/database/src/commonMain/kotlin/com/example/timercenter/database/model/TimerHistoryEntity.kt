package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Представляет запись истории использования таймера
 * @property id Уникальный идентификатор записи истории
 * @property timerId ID связанного таймера
 * @property name Имя таймера на момент записи
 * @property lastStartedTime Временная метка последнего запуска таймера
 */
@Entity(
    tableName = "timer_history",
    foreignKeys = [
        ForeignKey(entity = TimerEntity::class, parentColumns = ["id"], childColumns = ["timerId"], onDelete = ForeignKey.CASCADE),
    ]
)
data class TimerHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timerId: Int,
    val name: String,
    val lastStartedTime: Long,
)
