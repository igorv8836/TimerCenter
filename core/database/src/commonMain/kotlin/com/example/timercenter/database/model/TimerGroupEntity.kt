package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Представляет группу таймеров в базе данных
 * @property id Уникальный идентификатор группы таймеров
 * @property name Отображаемое имя группы таймеров
 * @property groupType Тип группы таймеров
 * @property isRunning Флаг, указывающий, запущена ли группа в данный момент
 * @property lastStartedTime Временная метка последнего запуска группы в миллисекундах
 * @property delayTime Время задержки в миллисекундах (используется при groupType - GroupType.DELAY)
 */
@Entity(tableName = "timer_groups")
data class TimerGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val groupType: Int,
    val isRunning: Boolean = false,
    val lastStartedTime: Long = 0L, // Время последнего запуска в миллисекундах
    val delayTime: Long = 0L // Время задержки в миллисекундах (при groupType - GroupType.DELAY)

)