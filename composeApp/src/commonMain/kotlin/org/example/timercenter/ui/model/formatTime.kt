package org.example.timercenter.ui.model

/**
 * Форматирует время в миллисекундах в строку формата "мм:сс"
 * @param timeMillis Время в миллисекундах
 * @return Отформатированная строка времени
 */
fun formatTime(timeMillis: Long): String {
    val allSeconds = timeMillis / 1000 + if (timeMillis % 1000 > 0) 1 else 0
    val seconds = (allSeconds) % 60
    val minutes = (allSeconds) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
