package org.example.timercenter.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.formatTime

/**
 * Компонент таймера с кнопками управления
 * @param timer Модель таймера
 * @param isSelected Флаг выбранного состояния
 * @param onSelect Обработчик выбора таймера
 * @param onStart Обработчик запуска таймера
 * @param onPause Обработчик паузы таймера
 * @param onStop Обработчик остановки таймера
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Timer(
    timer: TimerUiModel,
    isSelected: Boolean,
    onSelect: (isLongPress: Boolean) -> Unit,
    onStart: (Int) -> Unit,
    onPause: (Int) -> Unit,
    onStop: (Int) -> Unit,
) {
    val remainingTime = remember(timer) {
        mutableStateOf(calculateRemainingTime(timer))
    }

    LaunchedEffect(timer.isRunning) {
        while (timer.isRunning) {
            delay(1000L)
            remainingTime.value = calculateRemainingTime(timer)

            if (remainingTime.value <= 0) {
                // Остановка таймера при окончании времени
                onStop(timer.id)
            }
        }
    }

    val progress = remainingTime.value.toFloat() / timer.totalTime

    Row(
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = formatTime(remainingTime.value),
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
            if (!timer.isRunning && remainingTime.value == timer.totalTime) {
                CircularButton(icon = Icons.Default.PlayArrow, onClick = {
                    onStart(timer.id)
                })
            } else {
                CircularButton(
                    icon = if (timer.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    onClick = {
                        if (timer.isRunning) {
                            onPause(timer.id)
                        } else {
                            onStart(timer.id)
                        }
                    },
                    progress = if (timer.isRunning) progress else 1f
                )

                CircularButton(icon = Icons.Default.Stop, onClick = {
                    onStop(timer.id)
                })
            }
        }
    }
}

/**
 * Вычисляет оставшееся время таймера
 * @param timer Модель таймера
 * @return Оставшееся время в миллисекундах
 */
private fun calculateRemainingTime(timer: TimerUiModel): Long {
    return if (timer.isRunning) {
        val elapsed = Clock.System.now().toEpochMilliseconds() - timer.lastStartedTime
        maxOf(0, timer.remainingMillis - elapsed)
    } else {
        timer.remainingMillis
    }
}

/**
 * Круглая кнопка с иконкой и индикатором прогресса
 * @param icon Иконка кнопки
 * @param onClick Обработчик нажатия
 * @param progress Прогресс выполнения (от 0 до 1)
 */
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


