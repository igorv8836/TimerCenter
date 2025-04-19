package org.example.timercenter.data.scheduler

/**
 * Абстрактный класс планировщика таймеров
 * Определяет интерфейс для управления таймерами на разных платформах
 */
expect class TimerScheduler {
    /**
     * Планирует выполнение таймера
     * @param timerId Идентификатор таймера
     * @param delayMillis Задержка в миллисекундах
     */
    suspend fun scheduleTimer(timerId: Int, delayMillis: Long)

    /**
     * Отменяет выполнение таймера
     * @param timerId Идентификатор таймера
     */
    suspend fun cancelTimer(timerId: Int)
}