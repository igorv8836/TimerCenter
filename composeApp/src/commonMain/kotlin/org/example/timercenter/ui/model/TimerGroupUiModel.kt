package org.example.timercenter.ui.model

import kotlin.random.Random

data class TimerGroupUiModel(
    val id: Int,
    val groupName: String,
    val timers: List<TimerUiModel>
)

val exampleTimerGroupsList = createTimerGroupList(5)
fun exampleFindGroupTimer(id: Int) : TimerGroupUiModel? = exampleTimerGroupsList.getOrNull(id)

private fun createTimerGroupList(count: Int): List<TimerGroupUiModel> {
    return List(count) { index ->
        TimerGroupUiModel(
            id = index + 1,
            groupName = "Timer Group ${index + 1}",
            timers = exampleTimersList.shuffled().take(Random.nextInt(1, 6)) // Берем случайное количество таймеров от 1 до 5
        )
    }
}