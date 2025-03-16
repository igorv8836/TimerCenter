package org.example.timercenter.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.model.formatTime
import org.example.timercenter.ui.model.TimerUiModel



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Timer(
    timerAgoManager: TimeAgoManager,
    timer: TimerUiModel,
    isSelected: Boolean,
    onSelect: (isLongPress: Boolean) -> Unit,
    toRun: Boolean = false,
    onStart: () -> Unit
) {
    var remainingTime by remember { mutableStateOf(timer.totalTime) }
    var isRunning by remember { mutableStateOf(toRun) }
    var isStarted by remember { mutableStateOf(toRun) }

    val progress = remainingTime.toFloat() / timer.totalTime

    LaunchedEffect(isRunning) {
        while (isRunning && remainingTime > 0) {
            delay(1000L)
            remainingTime -= 1000L
        }
        if (remainingTime == 0L) {
            isRunning = false
            isStarted = false
        }
    }

    Row(
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = formatTime(remainingTime),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = timer.timerName,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (!isStarted) {
                CircularButton(icon = Icons.Default.PlayArrow, onClick = {
                    isRunning = true
                    isStarted = true
                    //Вызов метода onStart, который обрабатывается в HomeScreen, обновляя значения lastStartedTimer у этого таймера
                    onStart()
                    TimerManager.updateLastStartedTime(timerId = timer.id, currentTime = timerAgoManager.currentTimeMillis()) // Обновляем время запуска
                })
            } else {
                CircularButton(icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, onClick = {
                    isRunning = !isRunning
                }, progress = progress)

                CircularButton(icon = Icons.Default.Stop, onClick = {
                    isRunning = false
                    isStarted = false
                    remainingTime = timer.totalTime
                })
            }
        }
    }
}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun Timer(
//    timer: TimerUiModel,
//    isSelected: Boolean,
//    onSelect: (isLongPress: Boolean) -> Unit
//) {
//    var remainingTime by remember { mutableStateOf(timer.totalTime) }
//    var isRunning by remember { mutableStateOf(false) }
//    var isStarted by remember { mutableStateOf(false) }
//
//    val progress = remainingTime.toFloat() / timer.totalTime
//
//    LaunchedEffect(isRunning) {
//        while (isRunning && remainingTime > 0) {
//            delay(1000L)
//            remainingTime -= 1000L
//        }
//        if (remainingTime == 0L) {
//            isRunning = false
//            isStarted = false
//        }
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp)
//            .background(if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent, shape = RoundedCornerShape(8.dp))
//            .clip(RoundedCornerShape(8.dp))
//            .combinedClickable(
//                onClick = { onSelect(false) },
//                onLongClick = { onSelect(true) }
//            )
//            .padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = formatTime(remainingTime),
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = timer.timerName,
//                fontSize = 14.sp,
//                color = Color.Gray
//            )
//        }
//
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            if (!isStarted) {
//                CircularButton(icon = Icons.Default.PlayArrow, onClick = {
//                    isRunning = true
//                    isStarted = true
//                })
//            } else {
//                CircularButton(icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, onClick = {
//                    isRunning = !isRunning
//                }, progress = progress)
//
//                CircularButton(icon = Icons.Default.Stop, onClick = {
//                    isRunning = false
//                    isStarted = false
//                    remainingTime = timer.totalTime
//                })
//            }
//        }
//    }
//}
//

// Кнопка в виде круга (с анимацией обводки для кнопки "Пауза")
@Composable
fun CircularButton(icon: ImageVector, onClick: () -> Unit, progress: Float = 1f) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable(onClick = onClick)
    ) {
        if (progress < 1f) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxSize(),
                color = Color.Blue,
                strokeWidth = 3.dp,
                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            )
        }
        Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
    }
}


