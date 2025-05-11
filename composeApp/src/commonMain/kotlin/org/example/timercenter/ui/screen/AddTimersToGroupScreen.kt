package org.example.timercenter.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.timercenter.navigation.navigateToCreateGroup
import org.example.timercenter.ui.item.TimerGroupWithoutRun
import org.example.timercenter.ui.viewmodels.AddTimersToGroupViewModel
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEffect
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEvent
import org.koin.compose.viewmodel.koinViewModel

/**
 * Экран добавления таймеров в группу
 * @param navController Контроллер навигации
 * @param viewModel Модель представления экрана
 */
@Composable
fun AddTimersToGroupScreen(
    navController: NavController,
    viewModel: AddTimersToGroupViewModel = koinViewModel()
) {
    var isSelectGroup by remember { mutableStateOf(false) }

    val state by viewModel.container.stateFlow.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is AddTimersToGroupEffect.NavigateToEditTimerGroup -> {
                    navController.navigateToCreateGroup(effect.timerGroupId)
                }
            }
        }
    }

    if (!isSelectGroup) {
        LazyColumn {
            items(state.timerGroups.size) { index ->
                println(state.timerGroups[index])
                TimerGroupWithoutRun(
                    timerGroup = state.timerGroups[index],
                    id = state.timerGroups[index].id,
                    selectGroup = { id ->
                        isSelectGroup = true
                        viewModel.onEvent(AddTimersToGroupEvent.SetTimerChooseId(groupId = id))
                    })
            }
        }
    } else {
        isSelectGroup = false
        if (state.timerChooseId != null) {
            viewModel.onEvent(AddTimersToGroupEvent.ChooseGroupToEdit(state.timerChooseId!!))
        }
    }

}

