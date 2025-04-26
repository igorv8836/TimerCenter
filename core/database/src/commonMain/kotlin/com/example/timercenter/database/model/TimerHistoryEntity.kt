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
    val lastStartedTime: Long,
)

@Entity(
    tableName = "timer_group_history",
    foreignKeys = [
        ForeignKey(entity = TimerGroupEntity::class, parentColumns = ["id"], childColumns = ["timerGroupId"], onDelete = ForeignKey.CASCADE),
    ]
)
data class TimerGroupHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timerGroupId: Int,
    val lastStartedTime: Long,
)
