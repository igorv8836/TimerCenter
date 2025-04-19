package org.example.timercenter.data.scheduler

import org.koin.core.module.Module

/**
 * Модуль зависимостей для платформо-зависимых компонентов.
 * Предоставляет реализации WorkManager и NotificationManager.
 * @return Модуль Koin с настройками зависимостей
 */
expect fun timerSchedulerModule(): Module