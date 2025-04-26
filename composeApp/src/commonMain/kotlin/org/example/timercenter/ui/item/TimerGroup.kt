package org.example.timercenter.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerGroupUiModel

/**
 * Компонент группы таймеров с возможностью управления
 * @param timerGroup Модель группы таймеров
 * @param isSelected Флаг выбранного состояния
 * @param onSelect Обработчик выбора группы
 * @param onStartGroup Обработчик запуска группы
 * @param onPauseGroup Обработчик паузы группы
 * @param onResetGroup Обработчик сброса группы
 * @param toRun Флаг автоматического запуска при создании
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerGroup(
    timerGroup: TimerGroupUiModel,
    isSelected: Boolean,
    onSelect: (isLongPress: Boolean) -> Unit,
    onStartGroup: () -> Unit,
    onPauseGroup: () -> Unit,
    onResetGroup: () -> Unit,
) {
    var isRunning by remember { mutableStateOf(timerGroup.isRunning) }
    var isExpanded by remember { mutableStateOf(true) }

    val remainingTimes = remember {
        mutableStateListOf<Long>().apply {
            when (timerGroup.groupType) {
                GroupType.CONSISTENT -> {
                    if (!timerGroup.isStarted || !timerGroup.isRunning) {
                        addAll(timerGroup.timers.map { it.remainingMillis })
                    } else {
                        val elapsed = Clock.System.now().toEpochMilliseconds() - timerGroup.timers.first().lastStartedTime
                        addAll(timerGroup.timers.mapIndexed { index, timer ->
                            if (index == 0) maxOf(0, timer.remainingMillis - elapsed) else timer.remainingMillis
                        })
                    }
                }
                else -> {
                    addAll(timerGroup.timers.map {
                        if (!timerGroup.isStarted || !timerGroup.isRunning) {
                            it.remainingMillis
                        } else {
                            maxOf(0, it.remainingMillis - (Clock.System.now().toEpochMilliseconds() - it.lastStartedTime))
                        }
                    })
                }
            }
        }
    }

    val delayTime = timerGroup.delayTime

    LaunchedEffect(isRunning) {
        when (timerGroup.groupType) {
            GroupType.PARALLEL -> {
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
                while (isRunning && remainingTimes.any { it > 0 }) {
                    val activeTimerIndex = remainingTimes.indexOfFirst { it > 0 }
                    if (activeTimerIndex != -1) {
                        delay(1000L)
                        remainingTimes[activeTimerIndex] = remainingTimes[activeTimerIndex] - 1000
                    }
                }
            }

            GroupType.DELAY -> {
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
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
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

            if (!timerGroup.isStarted) {
                CircularButton(icon = Icons.Default.PlayArrow, onClick = {
                    isRunning = true
                    onStartGroup()
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
                    TimerWithoutButtons(
                        timer = timerGroup.timers[index],
                        remainingTime = remainingTimes[index]
                    )
                }
            }
        }
    }
}


