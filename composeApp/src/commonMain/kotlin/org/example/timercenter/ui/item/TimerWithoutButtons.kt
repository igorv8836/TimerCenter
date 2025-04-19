package org.example.timercenter.ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.formatTime

/**
 * Компонент таймера без кнопок управления
 * @param timer Модель таймера
 * @param remainingTime Оставшееся время в миллисекундах
 */
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