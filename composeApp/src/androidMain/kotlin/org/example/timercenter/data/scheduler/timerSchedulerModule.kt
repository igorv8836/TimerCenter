package org.example.timercenter.data.scheduler

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Модуль Koin для внедрения зависимостей планировщика таймеров
 * @return Модуль с зарегистрированным TimerScheduler
 */
actual fun timerSchedulerModule(): Module = module {
    single { TimerScheduler(androidContext()) }
}