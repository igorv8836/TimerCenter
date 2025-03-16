package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_history")
data class TimerHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timerId: Int,
    val name: String,
    val lastStartedTime: Long,
)
