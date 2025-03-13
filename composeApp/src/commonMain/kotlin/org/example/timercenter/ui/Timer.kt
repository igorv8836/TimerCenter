package org.example.timercenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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


@Composable
fun Timer(
    timer: TimerUiModel,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit
) {
    var remainingTime by remember { mutableStateOf(timer.totalTime) }
    var isRunning by remember { mutableStateOf(false) } // Идет ли сейчас таймер?
    var isStarted by remember { mutableStateOf(false) } // Был ли таймер запущен?

    val progress = remainingTime.toFloat() / timer.totalTime

    // Таймер обновляется каждую секунду
    LaunchedEffect(isRunning) {
        while (isRunning && remainingTime > 0) {
            delay(1000L)
            remainingTime -= 1000L
        }
        if (remainingTime == 0L) {
            isRunning = false
            isStarted = false // Когда время закончилось, сбрасываем
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Колонка с временем и названием таймера
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

        // Кнопки управления таймером
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isStarted) {
                CircularButton(icon = Icons.Default.PlayArrow, onClick = {
                    isRunning = true
                    isStarted = true
                    onStart()
                })
            } else {
                CircularButton(icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, onClick = {
                    isRunning = !isRunning
                    if (isRunning) onStart else onPause
                }, progress = progress)

                CircularButton(icon = Icons.Default.Stop, onClick = {
                    isRunning = false
                    isStarted = false
                    remainingTime = timer.totalTime
                    onReset()
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



// Функция для форматирования времени в "мм:сс"
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}


