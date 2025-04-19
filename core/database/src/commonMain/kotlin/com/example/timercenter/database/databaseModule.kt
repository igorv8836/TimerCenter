package com.example.timercenter.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.timercenter.common.MyDispatchers
import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupCrossRefDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.database.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Модуль для создания базы данных на конкретной платформе
 * @return Модуль с билдером базы данных
 */
expect fun platformDatabaseBuilderModule(): Module

/**
 * Модуль для работы с базой данных
 * @return Модуль с зависимостями базы данных
 */
fun databaseModule(): Module {
    return module {
        includes(platformDatabaseBuilderModule())

        single(createdAtStart = true) { provideDatabase(get(), get(named(MyDispatchers.IO))) }
        single<TimerDao> { get<AppDatabase>().getTimerDao() }
        single<TimerGroupDao> { get<AppDatabase>().getTimerGroupDao() }
        single<TimerHistoryDao> { get<AppDatabase>().getTimerHistoryDao() }
        single<TimerGroupCrossRefDao> { get<AppDatabase>().getTimerGroupCrossRefDao() }
    }
}

/**
 * Создает экземпляр базы данных
 * @param builder Билдер базы данных
 * @param coroutineContext Контекст корутин
 * @return Экземпляр базы данных
 */
private fun provideDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    coroutineContext: CoroutineDispatcher
) =
    builder
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(coroutineContext)
        .build()
