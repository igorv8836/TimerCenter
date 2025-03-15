package org.example.timercenter.ui.model

import kotlin.random.Random

enum class GroupType {
    CONSISTENT,
    PARALLEL,
    DELAY
}

data class TimerGroupUiModel(
    val id: Int,
    val groupName: String,
    val groupType: GroupType,
    val timers: List<TimerUiModel>
)

//var exampleTimerGroupsList = createTimerGroupList(5)
//fun exampleAddTimerGroup(group: TimerGroupUiModel) {
//    exampleTimerGroupsList = exampleTimerGroupsList + group
//}
//fun exampleChangeTimerGroupList(id: Int, groupName: String, groupType: GroupType, timers: List<TimerUiModel>) {
//    exampleTimerGroupsList.forEach { group ->
//        if (group.id == id) {
//            exampleTimerGroupsList = exampleTimerGroupsList - group
//            val changedGroup = group.copy(groupName = groupName, groupType = groupType, timers = timers)
//            exampleTimerGroupsList = exampleTimerGroupsList + changedGroup
//        }
//    }
//}
//
//// Вспомогательная функция для обновления таймера в группах
//internal fun updateGroupsWithEditedTimer(id: Int, newName: String, newTotalTime: Long) {
//    exampleTimerGroupsList = exampleTimerGroupsList.map { group ->
//        val updatedTimers = group.timers.map { timer ->
//            if (timer.id == id) timer.copy(timerName = newName, totalTime = newTotalTime)
//            else timer
//        }
//        group.copy(timers = updatedTimers)
//    }
//}
//
//fun exampleFindGroupTimer(id: Int) : TimerGroupUiModel? {
//    exampleTimerGroupsList.forEach { group ->
//        if (group.id == id) return group
//    }
//    return null
//}
//
//private fun createTimerGroupList(count: Int): List<TimerGroupUiModel> {
//    return List(count) { index ->
//        TimerGroupUiModel(
//            id = index + 1,
//            groupName = "Timer Group ${index + 1}",
//            groupType = GroupType.PARALLEL,
//            timers = exampleTimersList.shuffled().take(Random.nextInt(1, 6)) // Берем случайное количество таймеров от 1 до 5
//        )
//    }
//}