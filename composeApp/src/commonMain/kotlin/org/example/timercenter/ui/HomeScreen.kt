package org.example.timercenter.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(
    timers: List<TimerUiModel>,
    timerGroups: List<TimerGroupUiModel>
) {
    var isTimersExpanded by remember { mutableStateOf(true) }
    var isTimerGroupsExpanded by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Прокрутка всего экрана
            .padding(16.dp)
    ) {
        // Заголовок "Timers"
        if (timers.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Timers",
                    fontSize = 22.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                )
                // Кнопка сворачивания/разворачивания
                IconButton(onClick = { isTimersExpanded = !isTimersExpanded }) {
                    Icon(
                        imageVector = if (isTimersExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isTimersExpanded) "Collapse" else "Expand",
                    )
                }

            }
            if (isTimersExpanded) {
                // Список одиночных таймеров
                timers.forEach { timer ->
                    Timer(
                        timer = timer,
                        onStart = {},
                        onPause = {},
                        onReset = {}
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Заголовок "Timer Groups"
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Timer Groups",
                fontSize = 22.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
            )
            IconButton(onClick = { isTimerGroupsExpanded = !isTimerGroupsExpanded }) {
                Icon(
                    imageVector = if (isTimerGroupsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isTimerGroupsExpanded) "Collapse" else "Expand",
                )
            }
            
        }
        if (isTimersExpanded) {
            // Список одиночных таймеров
            timers.forEach { timer ->
                Timer(
                    timer = timer,
                    onStart = {},
                    onPause = {},
                    onReset = {}
                )
            }
        }

        // Список групп таймеров
        if (isTimerGroupsExpanded) {
            timerGroups.forEach { group ->
                TimerGroup(
                    timerGroup = group,
                    onStartGroup = {},
                    onPauseGroup = {},
                    onResetGroup = {}
                )
            }
        }
    }
}



//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(createTimerList(50))
//}