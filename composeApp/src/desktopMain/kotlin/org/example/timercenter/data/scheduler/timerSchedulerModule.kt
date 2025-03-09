package org.example.timercenter.data.scheduler

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun timerSchedulerModule(): Module = module {
    single { TimerScheduler() }
}