package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_groups")
data class TimerGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)