package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.item.TimerHistory
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.viewmodels.TimerHistoryViewModel
import org.example.timercenter.ui.viewmodels.states.TimerHistoryEvent
import org.example.timercenter.ui.viewmodels.states.TimerHistorySideEffect
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "HistoryScreen"

@Composable
fun HistoryScreen(
    timerAgoManager: TimeAgoManager,
    navController: NavController,
    historyViewModel: TimerHistoryViewModel = koinViewModel()
) {
    val state by historyViewModel.container.stateFlow.collectAsState()
    LaunchedEffect(historyViewModel) {
        historyViewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is TimerHistorySideEffect.ShowToast -> ""
                is TimerHistorySideEffect.NavigateToHomeRestartTimer -> navController.navigate("home/${effect.timerId}/-1")
                is TimerHistorySideEffect.NavigateToHomeRestartTimerGroup -> navController.navigate("home/-1/${effect.timerGroupId}")
            }
        }
    }
    println("$TAG Timers - ${state.timers}")
    println("$TAG TimerGroups - ${state.timerGroups}")

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
                        TimerManager.updateLastStartedTime(
                            item.id,
                            timerAgoManager.currentTimeMillis()
                        )
                        historyViewModel.onEvent(TimerHistoryEvent.NavigateToHomeRestartTimerEvent(timerId = item.id))
                    }
                )

                is TimerGroupUiModel -> TimerHistory(
                    name = item.groupName,
                    lastStartedTimeText = timerAgoManager.timeAgo(lastStartedTime),
                    onRestart = {
                        TimerManager.updateLastStartedTime(
                            item.id,
                            timerAgoManager.currentTimeMillis()
                        )
                        historyViewModel.onEvent(TimerHistoryEvent.NavigateToHomeRestartTimerGroupEvent(timerGroupId = item.id))
                    }
                )
            }

        }
    }
}

//@Composable
//fun HistoryScreen(
//    timerAgoManager: TimeAgoManager,
//    navController: NavController,
//    timers: List<TimerUiModel>,
//    timerGroups: List<TimerGroupUiModel>,
//    historyViewModel: TimerHistoryViewModel = viewModel()
//) {
//    val state by historyViewModel.container.stateFlow.collectAsState()
//    LaunchedEffect(historyViewModel) {
//        historyViewModel.container.sideEffectFlow.collect { effect ->
//            when (effect) {
//                is TimerHistorySideEffect.ShowToast -> ""
//                is TimerHistorySideEffect.NavigateToHomeRestartTimer -> navController.navigate("home/${effect.timerId}/-1")
//                is TimerHistorySideEffect.NavigateToHomeRestartTimerGroup -> navController.navigate("home/-1/${effect.timerGroupId}")
//            }
//        }
//    }
//    val historyItems = (timers.map { it to it.lastStartedTime } +
//            timerGroups.map { it to it.lastStartedTime })
//        .filter { it.second > 0L } // Убираем элементы, у которых lastStartedTime == 0
//        .sortedByDescending { it.second } // Сортировка по последнему запуску
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        items(historyItems.size) { index ->
//            val (item, lastStartedTime) = historyItems[index] // Получаем элемент по индексу
//
//            when (item) {
//                is TimerUiModel -> TimerHistory(
//                    timeAgoManager = timerAgoManager,
//                    name = item.timerName,
//                    lastStartedTime = lastStartedTime,
//                    onRestart = {
//                        TimerManager.updateLastStartedTime(
//                            item.id,
//                            timerAgoManager.currentTimeMillis()
//                        )
//                        navController.navigate("home/${item.id}/-1") {
//                            popUpTo("history") { inclusive = true }
//                        }
//                    }
//                )
//
//                is TimerGroupUiModel -> TimerHistory(
//                    timeAgoManager = timerAgoManager,
//                    name = item.groupName,
//                    lastStartedTime = lastStartedTime,
//                    onRestart = {
//                        TimerManager.updateLastStartedTime(
//                            item.id,
//                            timerAgoManager.currentTimeMillis()
//                        )
//                        navController.navigate("home/-1/${item.id}") {
//                            popUpTo("history") { inclusive = true }
//                        }
//                    }
//                )
//            }
//        }
//    }
//}

