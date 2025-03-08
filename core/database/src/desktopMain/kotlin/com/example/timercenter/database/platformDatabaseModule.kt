package com.example.timercenter.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timercenter.database.database.AppDatabase
import org.koin.dsl.module
import java.io.File

actual fun platformDatabaseBuilderModule() = module {
    single(createdAtStart = true) { provideDatabase() }
}

private fun provideDatabase(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath
    )
}