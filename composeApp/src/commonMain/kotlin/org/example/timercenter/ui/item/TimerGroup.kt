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
import org.example.timercenter.ui.model.TimerGroupUiModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerGroup(
    timerGroup: TimerGroupUiModel,
    isSelected: Boolean,
    onSelect: (isLongPress: Boolean) -> Unit,
    onStartGroup: () -> Unit,
    onPauseGroup: () -> Unit,
    onResetGroup: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    var isStarted by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(true) }

    val remainingTimes = remember {
        mutableStateListOf<Long>().apply { addAll(timerGroup.timers.map { it.totalTime }) }
    }

    LaunchedEffect(isRunning) {
        while (isRunning && remainingTimes.any { it > 0 }) {
            delay(1000L)
            remainingTimes.forEachIndexed { index, time ->
                if (time > 0) {
                    remainingTimes[index] = time - 1000
                }
            }
        }
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
            .padding(8.dp)
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
                    .padding(top = 8.dp)
                    .height(300.dp)
            ) {
                items(timerGroup.timers.size) { index ->
                    TimerWithoutButtons(timer = timerGroup.timers[index], remainingTime = remainingTimes[index])
                }
            }
        }
    }
}


