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
    val userHome = System.getProperty("user.home")
    val dbDir = File(userHome, ".timercenter")
    if (!dbDir.exists()) {
        dbDir.mkdirs()
    }
    val dbFile = File(dbDir, "app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath
    )
}