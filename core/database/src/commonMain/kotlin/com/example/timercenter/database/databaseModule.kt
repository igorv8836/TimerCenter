package com.example.timercenter.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.timercenter.common.MyDispatchers
import com.example.timercenter.database.dao.NewsDao
import com.example.timercenter.database.database.AppDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun platformDatabaseBuilderModule(): Module


fun databaseModule(): Module {
    return module {
        includes(platformDatabaseBuilderModule())

        single { provideDatabase(get(), get(named(MyDispatchers.IO))) }
        single<NewsDao> { get<AppDatabase>().getNewsDao() }
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
