package org.example.timercenter

import android.app.Application
import org.example.timercenter.di.KoinFactory
import org.koin.android.ext.koin.androidContext

/**
 * Основной класс приложения
 * Инициализирует Koin для внедрения зависимостей
 */
class App : Application() {
    /**
     * Инициализирует приложение и настраивает Koin
     */
    override fun onCreate() {
        super.onCreate()
        KoinFactory.setupKoin {
            androidContext(this@App)
        }
    }
}