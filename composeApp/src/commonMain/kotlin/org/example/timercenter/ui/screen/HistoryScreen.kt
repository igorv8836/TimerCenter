package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.orbit_mvi.compose.collectAsState
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.navigation.navigateToHome
import org.example.timercenter.ui.item.TimerHistory
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
    val state by historyViewModel.collectAsState()

    val historyItems = state.historyItems
        .filter { it.lastStartedTime > 0L }
        .sortedByDescending { it.lastStartedTime }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(historyItems) { item ->
            TimerHistory(
                name = item.name,
                lastStartedTimeText = timerAgoManager.timeAgo(item.lastStartedTime),
                onRestart = {
                    if (item.isTimer) {
                        navController.navigateToHome(timerId = item.id)
                    } else {
                        navController.navigateToHome(groupId = item.id)
                    }
                }
            )
        }
    }
}

