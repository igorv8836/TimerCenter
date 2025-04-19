package org.example.timercenter

import org.koin.dsl.module

/**
 * Android-реализация менеджера для форматирования времени в относительный формат
 */
actual class TimeAgoManager {
    /**
     * Преобразует timestamp в строку с относительным временем
     * @param lastStartedTime Временная метка в миллисекундах
     * @return Строка с относительным временем на английском языке
     */
    actual fun timeAgo(lastStartedTime: Long): String {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastStartedTime

        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 6 -> "a week ago"
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            seconds > 0 -> "$seconds second${if (seconds > 1) "s" else ""} ago"
            else -> "just now"
        }
    }

    /**
     * Возвращает текущее время в миллисекундах
     * @return Текущее время в миллисекундах
     */
    actual fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}

/**
 * Создает модуль Koin для TimeAgoManager
 * @return Модуль Koin с зарегистрированным TimeAgoManager
 */
actual fun timeManagerModule() = module {
    single { TimeAgoManager() }
}