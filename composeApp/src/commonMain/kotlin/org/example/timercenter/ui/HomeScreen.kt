package org.example.timercenter.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun HomeScreen(
    navController: NavController,
    timers: List<TimerUiModel>,
    timerGroups: List<TimerGroupUiModel>,
    onDeleteTimers: (List<TimerUiModel>) -> Unit,
    onEditTimer: (TimerUiModel) -> Unit,
) {
    var isTimersExpanded by remember { mutableStateOf(true) }
    var isTimerGroupsExpanded by remember { mutableStateOf(true) }

    var selectedTimers by remember { mutableStateOf(setOf<TimerUiModel>()) }

    val isSelectionMode = selectedTimers.isNotEmpty()

    Scaffold(
        topBar = {
            HomeTopBar(
                navController = navController,
                onSettingsClick = {},
                isSelectionMode = isSelectionMode,
                selectCount = selectedTimers.size,
                isEditEnabled = selectedTimers.size == 1,
                onDeleteClick = { onDeleteTimers(selectedTimers.toList()) },
                onEditClick = {
                    selectedTimers.firstOrNull()?.let { timer ->
                        navController.navigate("create/${timer.timerName}/${timer.totalTime}/${false}")
                    }

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding) // Добавляем padding от Scaffold
                .padding(16.dp)
                .pointerInput(Unit) { // Закрываем режим выбора при клике вне таймеров
                    detectTapGestures(onTap = { selectedTimers = emptySet() })
                }
        ) {
            if (timers.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Timers",
                        fontSize = 22.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )
                    IconButton(onClick = { isTimersExpanded = !isTimersExpanded }) {
                        Icon(
                            imageVector = if (isTimersExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isTimersExpanded) "Collapse" else "Expand",
                        )
                    }
                }

                if (isTimersExpanded) {
                    timers.forEach { timer ->
                        Timer(
                            timer = timer,
                            isSelected = selectedTimers.contains(timer),
                            onSelect = { isLongPress ->
                                selectedTimers = if (selectedTimers.contains(timer)) {
                                    selectedTimers - timer
                                } else {
                                    if (isLongPress || selectedTimers.isNotEmpty()) selectedTimers + timer else setOf(
                                        timer
                                    )
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            // Заголовок "Timer Groups"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
}
