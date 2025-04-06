package org.example.timercenter.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavController
import org.example.timercenter.navigation.navigateToCreateGroup
import org.example.timercenter.ui.item.TimerGroupWithoutRun
import org.example.timercenter.ui.viewmodels.AddTimersToGroupViewModel
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEffect
import org.example.timercenter.ui.viewmodels.states.AddTimersToGroupEvent
import org.koin.compose.viewmodel.koinViewModel


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

