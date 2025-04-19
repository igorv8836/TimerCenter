package org.example.timercenter

import org.koin.core.module.Module

/**
 * Менеджер для форматирования времени в относительный формат
 */
expect class TimeAgoManager {
    /**
     * Преобразует timestamp в строку с относительным временем
     * @param lastStartedTime Временная метка в миллисекундах
     * @return Строка с относительным временем
     */
    fun timeAgo(lastStartedTime: Long): String

    /**
     * Возвращает текущее время в миллисекундах
     * @return Текущее время в миллисекундах
     */
    fun currentTimeMillis(): Long
}

/**
 * Создает модуль Koin для TimeAgoManager
 * @return Модуль Koin
 */
expect fun timeManagerModule(): Module