package com.example.timercenter.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timercenter.database.database.AppDatabase
import org.koin.dsl.module

/**
 * Модуль Koin для создания базы данных на Android платформе
 * @return Модуль с зарегистрированным билдером базы данных
 */
actual fun platformDatabaseBuilderModule() = module {
    single(createdAtStart = true) { provideDatabase(get()) }
}

/**
 * Создает билдер базы данных для Android
 * @param context Контекст приложения
 * @return Билдер базы данных
 */
private fun provideDatabase(context: Context): RoomDatabase.Builder<AppDatabase> {
    val dbPath = context.applicationContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbPath.absolutePath
    )
}