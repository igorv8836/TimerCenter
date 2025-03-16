package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.item.TimerHistory
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.model.TimerUiModel

@Composable
fun HistoryScreen(
    timerAgoManager: TimeAgoManager,
    navController: NavController,
    timers: List<TimerUiModel>,
    timerGroups: List<TimerGroupUiModel>,
) {
    val historyItems = (timers.map { it to it.lastStartedTime } +
            timerGroups.map { it to it.lastStartedTime })
        .filter { it.second > 0L } // Убираем элементы, у которых lastStartedTime == 0
        .sortedByDescending { it.second } // Сортировка по последнему запуску

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(historyItems.size) { index ->
            val (item, lastStartedTime) = historyItems[index] // Получаем элемент по индексу

            when (item) {
                is TimerUiModel -> TimerHistory(
                    timeAgoManager = timerAgoManager,
                    name = item.timerName,
                    lastStartedTime = lastStartedTime,
                    onRestart = {
                        TimerManager.updateLastStartedTime(
                            item.id,
                            timerAgoManager.currentTimeMillis()
                        )
                        navController.navigate("home/${item.id}/-1") {
                            popUpTo("history") { inclusive = true }
                        }
                    }
                )

                is TimerGroupUiModel -> TimerHistory(
                    timeAgoManager = timerAgoManager,
                    name = item.groupName,
                    lastStartedTime = lastStartedTime,
                    onRestart = {
                        TimerManager.updateLastStartedTime(
                            item.id,
                            timerAgoManager.currentTimeMillis()
                        )
                        navController.navigate("home/-1/${item.id}") {
                            popUpTo("history") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

