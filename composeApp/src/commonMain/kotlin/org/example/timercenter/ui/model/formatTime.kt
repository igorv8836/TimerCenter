package org.example.timercenter.ui.model

// Функция для форматирования времени в "мм:сс"
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
