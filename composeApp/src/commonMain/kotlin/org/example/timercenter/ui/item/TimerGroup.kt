package org.example.timercenter.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerManager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerGroup(
    timerAgoManager: TimeAgoManager,
    timerGroup: TimerGroupUiModel,
    isSelected: Boolean,
    onSelect: (isLongPress: Boolean) -> Unit,
    onStartGroup: () -> Unit,
    onPauseGroup: () -> Unit,
    onResetGroup: () -> Unit,
    toRun: Boolean = false
) {
    var isRunning by remember { mutableStateOf(toRun) }
    var isStarted by remember { mutableStateOf(toRun) }
    var isExpanded by remember { mutableStateOf(true) }

    val remainingTimes = remember {
        mutableStateListOf<Long>().apply { addAll(timerGroup.timers.map { it.totalTime }) }
    }

    // Логика для задержки между таймерами
    val delayTime = timerGroup.delayTime // Время задержки для DELAY

    LaunchedEffect(isRunning) {
        when (timerGroup.groupType) {
            GroupType.PARALLEL -> {
                // Параллельный запуск таймеров
                while (isRunning && remainingTimes.any { it > 0 }) {
                    delay(1000L)
                    remainingTimes.forEachIndexed { index, time ->
                        if (time > 0) {
                            remainingTimes[index] = time - 1000
                        }
                    }
                }
            }
            GroupType.CONSISTENT -> {
                // Последовательный запуск таймеров
                while (isRunning && remainingTimes.any { it > 0 }) {
                    val activeTimerIndex = remainingTimes.indexOfFirst { it > 0 }
                    if (activeTimerIndex != -1) {
                        // Запуск следующего таймера только после окончания предыдущего
                        delay(1000L)
//                        remainingTimes[activeTimerIndex] -= 1000
                        remainingTimes[activeTimerIndex] = remainingTimes[activeTimerIndex] - 1000

                    }
                }
            }
            GroupType.DELAY -> {
                // Таймеры с задержкой между запуском
                while (isRunning && remainingTimes.any { it > 0 }) {
                    remainingTimes.forEachIndexed { index, time ->
                        if (time > 0) {
                            delay(1000L)
                            remainingTimes[index] = time - 1000
                        }
                    }
                    // После завершения всех таймеров мы вводим задержку между стартами
                    delay(delayTime)
                }
            }
        }

        // Завершаем все таймеры, когда они все закончены
        if (remainingTimes.all { it == 0L }) {
            isRunning = false
            isStarted = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = { onSelect(false) },
                onLongClick = { onSelect(true) }
            )
            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = timerGroup.groupName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!isStarted) {
                CircularButton(icon = Icons.Default.PlayArrow, onClick = {
                    isRunning = true
                    isStarted = true
                    onStartGroup()
                    TimerManager.updateLastStartedTimeForGroup(groupId = timerGroup.id, currentTime = timerAgoManager.currentTimeMillis())
                })
            } else {
                CircularButton(
                    icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    onClick = {
                        isRunning = !isRunning
                        if (isRunning) onStartGroup() else onPauseGroup()
                    }
                )
                CircularButton(icon = Icons.Default.Stop, onClick = {
                    isRunning = false
                    isStarted = false
                    remainingTimes.forEachIndexed { index, _ ->
                        remainingTimes[index] = timerGroup.timers[index].totalTime
                    }
                    onResetGroup()
                })
            }
        }

        if (isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp)
                    .heightIn(min = 80.dp, max = 320.dp)
            ) {
                items(timerGroup.timers.size) { index ->
                    TimerWithoutButtons(timer = timerGroup.timers[index], remainingTime = remainingTimes[index])
                }
            }
        }
    }
}


