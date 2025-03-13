package org.example.timercenter.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun HomeScreen(timers: List<TimerUiModel>) {
    Column {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .height(300.dp)
        ) {
            items(timers.size) { index ->
                Timer(timer = timers[index],
                    onStart = {},
                    onPause = {},
                    onReset = {})
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Timer Groups",
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 12.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground),
        )

    }
}

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


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(createTimerList(50))
}