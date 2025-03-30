package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "timer_group_cross_ref",
    primaryKeys = ["timerId", "groupId"],
    foreignKeys = [
        ForeignKey(entity = TimerEntity::class, parentColumns = ["id"], childColumns = ["timerId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = TimerGroupEntity::class, parentColumns = ["id"], childColumns = ["groupId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class TimerGroupCrossRef(
    val timerId: Int,
    val groupId: Int,
)