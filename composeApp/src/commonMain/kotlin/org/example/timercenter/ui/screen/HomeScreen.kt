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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.HomeTopBar
import org.example.timercenter.ui.PopupMessage
import org.example.timercenter.ui.item.Timer
import org.example.timercenter.ui.item.TimerGroup
import org.example.timercenter.ui.viewmodels.HomeViewModel
import org.example.timercenter.ui.viewmodels.states.HomeEffect
import org.example.timercenter.ui.viewmodels.states.HomeEvent

@Composable
fun HomeScreen(
    timerAgoManager: TimeAgoManager, navController: NavController, homeViewModel: HomeViewModel = viewModel()
) {
    val state by homeViewModel.container.stateFlow.collectAsState()
    LaunchedEffect(homeViewModel) {
        homeViewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToSettings -> navController.navigate("settings")
                is HomeEffect.NavigateToEditTimer -> navController.navigate("create/${effect.timerId}")
                is HomeEffect.NavigateToEditTimerGroup -> navController.navigate("create_group/${effect.timerGroupId}")
            }
        }
    }
    var isTimersExpanded by remember { mutableStateOf(true) }
    var isTimerGroupsExpanded by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            val isEditEnabled =
                (state.selectedTimers.size == 1 && state.selectedTimerGroups.isEmpty())
                        || (state.selectedTimerGroups.size == 1 && state.selectedTimers.isEmpty())
            HomeTopBar(
                onSettingsClick = { homeViewModel.onEvent(HomeEvent.NavigateToSettingsEvent) },
                isSelectionMode = state.selectedTimers.isNotEmpty() || state.selectedTimerGroups.isNotEmpty(),
                selectCount = state.selectedTimers.size + state.selectedTimerGroups.size,
                isEditEnabled = isEditEnabled,
                onDeleteClick = { homeViewModel.onEvent(HomeEvent.ShowDeleteConfirmation) },
                onEditClick = { homeViewModel.onEvent(HomeEvent.EditSelected) })
        }) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(innerPadding).padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { homeViewModel.onEvent(HomeEvent.ClearSelection) })
                }) {
            if (state.timers.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Таймеры",
                        fontSize = 22.sp,
                        style = TextStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )
                    IconButton(onClick = { isTimersExpanded = !isTimersExpanded }) {
                        Icon(
                            imageVector = if (isTimersExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isTimersExpanded) "Collapse" else "Expand"
                        )
                    }
                }
                if (isTimersExpanded) {
                    state.timers.forEach { timer ->
                        Timer(
                            timerAgoManager = timerAgoManager,
                            timer = timer,
                            isSelected = state.selectedTimers.contains(timer),
                            onSelect = { isLongPress ->
                                homeViewModel.onEvent(HomeEvent.ToggleTimerSelection(timer, isLongPress))
                            },
                            toRun = timer.id == state.timerRestartId,
                            onStart = {
                                homeViewModel.onEvent(HomeEvent.UpdateTimerLastStartedTime(timerId = timer.id, lastStartedTime = timerAgoManager.currentTimeMillis()))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
            if (state.showDeleteConfirmation) {
                val text = when {
                    state.selectedTimers.isEmpty() -> if (state.selectedTimerGroups.size == 1)
                        "Вы уверены, что хотите удалить эту группу?"
                    else
                        "Вы уверены, что хотите удалить эти группы?"

                    state.selectedTimerGroups.isEmpty() -> if (state.selectedTimers.size == 1)
                        "Вы уверены, что хотите удалить этот таймер?"
                    else
                        "Вы уверены, что хотите удалить эти таймеры?"

                    else -> "Вы уверены, что хотите удалить эти таймеры и группы?"
                }
                PopupMessage(
                    message = text,
                    buttonText = "Удалить",
                    onCancel = { homeViewModel.onEvent(HomeEvent.CancelDeletion) },
                    onConfirm = { homeViewModel.onEvent(HomeEvent.ConfirmDeletion) })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Группы таймеров",
                    fontSize = 22.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                )
                IconButton(onClick = { isTimerGroupsExpanded = !isTimerGroupsExpanded }) {
                    Icon(
                        imageVector = if (isTimerGroupsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isTimerGroupsExpanded) "Collapse" else "Expand"
                    )
                }
            }
            if (isTimerGroupsExpanded) {
                state.timerGroups.forEach { group ->
                    TimerGroup(
                        timerAgoManager = timerAgoManager,
                        timerGroup = group,
                        isSelected = state.selectedTimerGroups.contains(group),
                        onSelect = { isLongPress ->
                            homeViewModel.onEvent(HomeEvent.ToggleTimerGroupSelection(group, isLongPress))
                        },
                        onStartGroup = {},
                        onPauseGroup = {},
                        onResetGroup = {},
                        toRun = group.id == state.timerGroupRestartId
                    )
                }
            }
        }
    }
}
