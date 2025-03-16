package org.example.timercenter.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.item.TimerHistory
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.model.formatTime
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

