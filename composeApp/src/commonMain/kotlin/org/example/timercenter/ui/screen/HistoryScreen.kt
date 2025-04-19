package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.navigation.navigateToHome
import org.example.timercenter.ui.item.TimerHistory
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.viewmodels.TimerHistoryViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Экран истории таймеров
 * @param timerAgoManager Менеджер для получения текста, описывающего время
 * @param navController Навигатор для перехода на другие экраны
 * @param historyViewModel ViewModel для управления историей таймеров
 */
@Composable
fun HistoryScreen(
    timerAgoManager: TimeAgoManager,
    navController: NavController,
    historyViewModel: TimerHistoryViewModel = koinViewModel()
) {
    val state by historyViewModel.container.stateFlow.collectAsState()

    val historyItems = (state.timers.map { it to it.lastStartedTime } +
            state.timerGroups.map { it to it.lastStartedTime })
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
                    name = item.timerName,
                    lastStartedTimeText = timerAgoManager.timeAgo(lastStartedTime),
                    onRestart = {
                        navController.navigateToHome(timerId = item.id)
                    }
                )

                is TimerGroupUiModel -> TimerHistory(
                    name = item.groupName,
                    lastStartedTimeText = timerAgoManager.timeAgo(lastStartedTime),
                    onRestart = {
                        navController.navigateToHome(groupId = item.id)
                    }
                )
            }

        }
    }
}

