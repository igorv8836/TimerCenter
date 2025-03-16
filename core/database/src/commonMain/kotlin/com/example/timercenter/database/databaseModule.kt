package com.example.timercenter.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.timercenter.common.MyDispatchers
import com.example.timercenter.database.dao.TimerDao
import com.example.timercenter.database.dao.TimerGroupDao
import com.example.timercenter.database.dao.TimerHistoryDao
import com.example.timercenter.database.database.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun platformDatabaseBuilderModule(): Module


fun databaseModule(): Module {
    return module {
        includes(platformDatabaseBuilderModule())

        single(createdAtStart = true) { provideDatabase(get(), get(named(MyDispatchers.IO))) }
        single<TimerDao> { get<AppDatabase>().getTimerDao() }
        single<TimerGroupDao> { get<AppDatabase>().getTimerGroupDao() }
        single<TimerHistoryDao> { get<AppDatabase>().getTimerHistoryDao() }
    }
}

private fun provideDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    coroutineContext: CoroutineDispatcher
) =
    builder
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(coroutineContext)
        .build()
