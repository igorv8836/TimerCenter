@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.timercenter.database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupCrossRefDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.dao.TimerGroupHistoryDao
import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.dao.TimerRunDao
import com.example.timercenter.database.model.TimerEntity
import com.example.timercenter.database.model.TimerGroupCrossRef
import com.example.timercenter.database.model.TimerGroupEntity
import com.example.timercenter.database.model.TimerGroupHistoryEntity
import com.example.timercenter.database.model.TimerHistoryEntity
import com.example.timercenter.database.model.TimerRunEntity

/**
 * База данных приложения
 */
@Database(
    entities = [
        TimerEntity::class,
        TimerGroupEntity::class,
        TimerHistoryEntity::class,
        TimerGroupCrossRef::class,
        TimerRunEntity::class,
        TimerGroupHistoryEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    /**
     * Получает DAO для работы с таймерами
     * @return DAO для работы с таймерами
     */
    abstract fun getTimerDao(): TimerDao

    /**
     * Получает DAO для работы с запусками таймера
     * @return DAO для работы с запусками таймера
     */
    abstract fun getTimerRunDao(): TimerRunDao

    /**
     * Получает DAO для работы с группами таймеров
     * @return DAO для работы с группами таймеров
     */
    abstract fun getTimerGroupDao(): TimerGroupDao

    /**
     * Получает DAO для работы с историей таймеров
     * @return DAO для работы с историей таймеров
     */
    abstract fun getTimerHistoryDao(): TimerHistoryDao

    /**
     * Получает DAO для работы со связями между таймерами и группами
     * @return DAO для работы со связями
     */
    abstract fun getTimerGroupCrossRefDao(): TimerGroupCrossRefDao

    abstract fun getTimerGroupHistoryDao(): TimerGroupHistoryDao
}

/**
 * Конструктор базы данных
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase>