package org.example.timercenter.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.HomeTopBar
import org.example.timercenter.ui.PopupMessage
import org.example.timercenter.ui.item.Timer
import org.example.timercenter.ui.item.TimerGroup
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerUiModel


@Composable
fun HomeScreen(
    timerAgoManager: TimeAgoManager,
    navController: NavController,
    timers: List<TimerUiModel>,
    timerGroups: List<TimerGroupUiModel>,
    timerRestartId: Int? = null,
    timerGroupRestartId: Int? = null,
    onDeleteTimers: (List<TimerUiModel>) -> Unit,
    onDeleteGroupTimers: (List<TimerGroupUiModel>) -> Unit,
) {
    var isTimersExpanded by remember { mutableStateOf(true) }
    var isTimerGroupsExpanded by remember { mutableStateOf(true) }

    var selectedTimers by remember { mutableStateOf(setOf<TimerUiModel>()) }
    var selectedTimerGroups by remember { mutableStateOf(setOf<TimerGroupUiModel>()) }
    val isSelectionMode = selectedTimers.isNotEmpty() || selectedTimerGroups.isNotEmpty()
    var showPopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            val isEditEnabled =
                (selectedTimers.size == 1 && selectedTimerGroups.isEmpty()) || (selectedTimerGroups.size == 1 && selectedTimers.isEmpty())
            HomeTopBar(
                onSettingsClick = {
                    navController.navigate("settings")
                },
                isSelectionMode = isSelectionMode,
                selectCount = selectedTimers.size + selectedTimerGroups.size,
                isEditEnabled = isEditEnabled,
                onDeleteClick = {
                    showPopup = true
                },
                onEditClick = {
                    if (selectedTimers.isNotEmpty()) {
                        selectedTimers.firstOrNull()?.let { timer ->
                            navController.navigate("create/${timer.id}")
                        }
                    } else if (selectedTimerGroups.isNotEmpty()) {
                        println("selectedTimerGroups.size - ${selectedTimerGroups.size}")
                        selectedTimerGroups.firstOrNull()?.let { group ->
                            println("group - $group")
                            navController.navigate("create_group/${group.id}")
                        }
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
                        text = "Таймеры",
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
                            timerAgoManager = timerAgoManager,
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
                            },
                            toRun = timer.id == timerRestartId
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            if (showPopup) {
                val text = if (selectedTimers.isEmpty()) {
                    if (selectedTimerGroups.size == 1) "Вы уверены, что хотите удалить эту группу?"
                    else "Вы уверены, что хотите удалить эти группы?"
                } else if (selectedTimerGroups.isEmpty()) {
                    if (selectedTimers.size == 1) "Вы уверены, что хотите удалить этот таймер?"
                    else "Вы уверены, что хотите удалить эти таймеры?"
                } else "Вы уверены, что хотите удалить эти таймеры и группы?"
                PopupMessage(
                    message = text,
                    buttonText = "Удалить",
                    onCancel = { showPopup = false },
                    onConfirm = {
                        showPopup = false
                        onDeleteGroupTimers(selectedTimerGroups.toList())
                        onDeleteTimers(selectedTimers.toList())
                    })
            }

            // Заголовок "Timer Groups"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Группы таймеров",
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
                        timerAgoManager = timerAgoManager,
                        timerGroup = group,
                        isSelected = selectedTimerGroups.contains(group),
                        onSelect = { isLongPress ->
                            selectedTimerGroups = if (selectedTimerGroups.contains(group)) {
                                selectedTimerGroups - group
                            } else {
                                if (isLongPress || selectedTimerGroups.isNotEmpty()) selectedTimerGroups + group else setOf(
                                    group
                                )
                            }
                        },
                        onStartGroup = {},
                        onPauseGroup = {},
                        onResetGroup = {},
                        toRun = group.id == timerGroupRestartId
                    )
                }
            }
        }
    }
}
