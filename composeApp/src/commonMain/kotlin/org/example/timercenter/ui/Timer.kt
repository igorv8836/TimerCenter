package org.example.timercenter.ui

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

data class TimerUiModel(
    val timerName: String,
    val totalTime: Long = 60_000L
)

fun createTimerList(count: Int): List<TimerUiModel> {
    return List(count) { index ->
        TimerUiModel(
            timerName = "Timer ${index + 1}",
//            totalTime = Random.nextLong(30_000L, 300_000L) // от 30 секунд до 5 минут
            totalTime = 300_000L // от 30 секунд до 5 минут
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Timer(
    timer: TimerUiModel,
    isSelected: Boolean,
    onSelect: (isLongPress: Boolean) -> Unit
) {
    var remainingTime by remember { mutableStateOf(timer.totalTime) }
    var isRunning by remember { mutableStateOf(false) }
    var isStarted by remember { mutableStateOf(false) }

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
            .padding(8.dp),
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
                progress = progress,
                modifier = Modifier.fillMaxSize(),
                color = Color.Blue,
                strokeWidth = 3.dp
            )
        }
        Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
    }
}

@Composable
fun TimerWithoutButtons(timer: TimerUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = formatTime(timer.totalTime),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = timer.timerName,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
@Composable
fun TimerWithoutButtons(timer: TimerUiModel, remainingTime: Long) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = formatTime(remainingTime), // Отображаем оставшееся время
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = timer.timerName,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}



// Функция для форматирования времени в "мм:сс"
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}


