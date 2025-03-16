package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val durationMillis: Long,
    val remainingMillis: Long = durationMillis,
    val groupId: Int? = null,
    val isRunning: Boolean = false,
    val startTime: Long? = null,
    val status: TimerStatus = TimerStatus.NOT_STARTED
)

enum class TimerStatus {
    RUNNING,
    PAUSED,
    NOT_STARTED,
}