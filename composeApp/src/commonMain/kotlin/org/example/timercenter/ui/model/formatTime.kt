package org.example.timercenter.ui.model

/**
 * Форматирует время в миллисекундах в строку формата "мм:сс"
 * @param timeMillis Время в миллисекундах
 * @return Отформатированная строка времени
 */
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
