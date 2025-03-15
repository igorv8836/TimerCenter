package org.example.timercenter.ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

object TimerManager {
    private var nextTimerId = 1
    private var nextGroupId = 1

    var timers by mutableStateOf<List<TimerUiModel>>(emptyList())
        private set

    var timerGroups by mutableStateOf<List<TimerGroupUiModel>>(emptyList())
        private set

    init {
        // Инициализируем тестовыми данными
        timers = createExampleTimers(5)
        timerGroups = createExampleTimerGroups(5)
    }

    // Находит новый уникальный ID
//    fun generateNewTimerId(): Int {
//        return (exampleTimersList.maxOfOrNull { it.id } ?: 0) + 1
//    }

    /** Создать новый таймер */
    fun addTimer(timerName: String, totalTime: Long) {
        val newTimer = TimerUiModel(
            id = nextTimerId++,
            timerName = timerName,
            totalTime = totalTime
        )
        timers = timers + newTimer
    }

    /** Удалить таймеры */
    fun deleteTimers(timersToDelete: List<TimerUiModel>) {
        val timerIdsToDelete = timersToDelete.map { it.id }.toSet()

        // Удаляем таймеры из списка
        timers = timers.filterNot { it.id in timerIdsToDelete }

        // Удаляем таймеры из групп
        timerGroups = timerGroups.map { group ->
            group.copy(timers = group.timers.filterNot { it.id in timerIdsToDelete })
        }
    }

    /** Найти таймер */
    fun findTimer(id: Int): TimerUiModel? = timers.find { it.id == id }

    /** Изменить таймер */
    fun editTimer(id: Int, newName: String, newTotalTime: Long) {
        timers = timers.map { timer ->
            if (timer.id == id) timer.copy(timerName = newName, totalTime = newTotalTime) else timer
        }

        // Обновляем таймер в группах
        timerGroups = timerGroups.map { group ->
            val updatedTimers = group.timers.map { timer ->
                if (timer.id == id) timer.copy(timerName = newName, totalTime = newTotalTime) else timer
            }
            group.copy(timers = updatedTimers)
        }
    }

    /** Добавить группу */
    fun addTimerGroup(groupName: String, groupType: GroupType, timers: List<TimerUiModel>) {
        val newGroup = TimerGroupUiModel(
            id = nextGroupId++,
            groupName = groupName,
            groupType = groupType,
            timers = timers
        )
        timerGroups = timerGroups + newGroup
    }

    /** Удалить группы */
    fun deleteTimerGroups(groupsToDelete: List<TimerGroupUiModel>) {
        val groupIdsToDelete = groupsToDelete.map { it.id }.toSet()
        timerGroups = timerGroups.filterNot { it.id in groupIdsToDelete }
    }

    /** Найти группу */
    fun findTimerGroup(id: Int): TimerGroupUiModel? = timerGroups.find { it.id == id }

    /** Изменить группу */
    fun editTimerGroup(id: Int, newName: String, newType: GroupType, newTimers: List<TimerUiModel>) {
        timerGroups = timerGroups.map { group ->
            if (group.id == id) {
                group.copy(groupName = newName, groupType = newType, timers = newTimers)
            } else group
        }
    }

    /** Создание тестовых таймеров */
    private fun createExampleTimers(count: Int): List<TimerUiModel> {
        return List(count) { index ->
            TimerUiModel(
                id = nextTimerId++,
                timerName = "Timer ${index + 1}",
                totalTime = Random.nextLong(30_000, 300_000) // Время от 30 сек до 5 мин
            )
        }
    }

    /** Создание тестовых групп */
    private fun createExampleTimerGroups(count: Int): List<TimerGroupUiModel> {
        return List(count) { index ->
            TimerGroupUiModel(
                id = nextGroupId++,
                groupName = "Timer Group ${index + 1}",
                groupType = GroupType.values().random(),
                timers = timers.shuffled().take(Random.nextInt(1, 4)) // От 1 до 3 таймеров в группе
            )
        }
    }
}

