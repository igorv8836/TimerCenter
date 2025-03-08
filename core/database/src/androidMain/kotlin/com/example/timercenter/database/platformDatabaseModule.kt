package com.example.timercenter.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timercenter.database.database.AppDatabase
import org.koin.dsl.module

actual fun platformDatabaseBuilderModule() = module {
    single(createdAtStart = true) { provideDatabase(get()) }
}

private fun provideDatabase(context: Context): RoomDatabase.Builder<AppDatabase> {
    val dbPath = context.applicationContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbPath.absolutePath
    )
}