package org.example.timercenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

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


// Функция для форматирования времени в "мм:сс"
fun formatTime(timeMillis: Long): String {
    val seconds = (timeMillis / 1000) % 60
    val minutes = (timeMillis / 1000) / 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}


