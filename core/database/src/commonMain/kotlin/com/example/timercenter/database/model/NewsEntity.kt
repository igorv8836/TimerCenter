package com.example.timercenter.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long?
)