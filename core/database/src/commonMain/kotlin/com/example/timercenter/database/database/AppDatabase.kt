@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.timercenter.database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupEntity
import com.example.timercenter.database.model.TimerHistoryEntity

@Database(
    entities = [
        TimerEntity::class,
        TimerGroupEntity::class,
        TimerHistoryEntity::class,
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getTimerDao(): TimerDao
    abstract fun getTimerGroupDao(): TimerGroupDao
    abstract fun getTimerHistoryDao(): TimerHistoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase>