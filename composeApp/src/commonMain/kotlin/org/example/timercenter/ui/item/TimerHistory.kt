package org.example.timercenter.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Компонент для отображения истории таймера
 * @param name Название таймера
 * @param lastStartedTimeText Текст времени последнего запуска
 * @param onRestart Обработчик перезапуска таймера
 */
@Composable
fun TimerHistory(
    name: String,
    lastStartedTimeText: String,
    onRestart: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = lastStartedTimeText,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        Button(onClick = onRestart) {
            Text("Запустить")
        }
    }
}


//@Composable
//fun TimerHistory(
//    timeAgoManager: TimeAgoManager,
//    name: String,
//    lastStartedTime: Long,
//    onRestart: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column {
//            Text(
//                text = name,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp
//            )
//            Text(
//                text = timeAgoManager.timeAgo(lastStartedTime),
//                color = Color.Gray,
//                fontSize = 14.sp
//            )
//        }
//        Button(onClick = onRestart) {
//            Text("Запустить")
//        }
//    }
//}
