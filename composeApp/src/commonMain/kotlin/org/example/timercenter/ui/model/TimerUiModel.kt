package org.example.timercenter.ui.model

import kotlin.random.Random

data class TimerUiModel(
    val id: Int,
    val timerName: String,
    val totalTime: Long = 60_000L
)

val exampleTimersList = createTimerList(10)
fun exampleFindTimer(id: Int) : TimerUiModel? = exampleTimersList.getOrNull(id)

private fun createTimerList(count: Int): List<TimerUiModel> {
    return List(count) { index ->
        TimerUiModel(
            id = index + 1,
            timerName = "Timer ${index + 1}",
            totalTime = Random.nextLong(30_000L, 300_000L) // от 30 секунд до 5 минут
//            totalTime = 300_000L // от 30 секунд до 5 минут
        )
    }
}

// Функция для форматирования времени в "мм:сс"
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
