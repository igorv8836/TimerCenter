package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_groups")
data class TimerGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val groupType: Int,
    val isRunning: Boolean = false,
    val lastStartedTime: Long = 0L, // Время последнего запуска в миллисекундах
    val delayTime: Long = 0L // Время задержки в миллисекундах (при groupType - GroupType.DELAY)

)