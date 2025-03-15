package org.example.timercenter.ui.model

import kotlin.random.Random

data class TimerUiModel(
    val id: Int,
    val timerName: String,
    val totalTime: Long = 60_000L
)


//var exampleTimersList = createTimerList(10)
//fun exampleFindTimer(id: Int) : TimerUiModel? {
//    exampleTimersList.forEach { timer ->
//        if (timer.id == id) return timer
//    }
//    return null
//}
//// Функция добавления таймера
//fun exampleAddTimer(timerName: String, totalMilliseconds: Long) {
//    val newTimer = TimerUiModel(
//        id = generateNewTimerId(),
//        timerName = timerName,
//        totalTime = totalMilliseconds
//    )
//    exampleTimersList = exampleTimersList + newTimer
//}
//
//// Функция удаления таймера
//fun deleteTimers(timersToDelete: List<TimerUiModel>) {
//    val timerIdsToDelete = timersToDelete.map { it.id }.toSet()
//
//    // Удаляем таймеры из общего списка
//    exampleTimersList = exampleTimersList.filterNot { it.id in timerIdsToDelete }
//
//    // Удаляем таймеры из групп
//    exampleTimerGroupsList = exampleTimerGroupsList.map { group ->
//        val updatedTimers = group.timers.filterNot { it.id in timerIdsToDelete }
//        group.copy(timers = updatedTimers)
//    }
//}
//
//// Находит новый уникальный ID
//fun generateNewTimerId(): Int {
//    return (exampleTimersList.maxOfOrNull { it.id } ?: 0) + 1
//}
//
//fun exampleEditTimer(id: Int, newName: String, newTotalTime: Long) {
//    exampleTimersList = exampleTimersList.map { timer ->
//        if (timer.id == id) timer.copy(timerName = newName, totalTime = newTotalTime)
//        else timer
//    }
//    // Обновляем таймеры в группах
//    updateGroupsWithEditedTimer(id, newName, newTotalTime)
//}
//
//private fun createTimerList(count: Int): List<TimerUiModel> {
//    return List(count) { index ->
//        TimerUiModel(
//            id = index + 1,
//            timerName = "Timer ${index + 1}",
//            totalTime = Random.nextLong(30_000L, 300_000L) // от 30 секунд до 5 минут
////            totalTime = 300_000L // от 30 секунд до 5 минут
//        )
//    }
//}
