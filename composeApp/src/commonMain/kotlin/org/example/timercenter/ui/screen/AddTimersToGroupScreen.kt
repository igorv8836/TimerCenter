package org.example.timercenter.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavController
import org.example.timercenter.ui.item.TimerGroupWithoutRun
import org.example.timercenter.ui.model.TimerGroupUiModel


@Composable
fun AddTimersToGroupScreen(timerGroups: List<TimerGroupUiModel>, navController: NavController) {
    var isSelectGroup by remember { mutableStateOf(false) }
    var chosenGroup by remember { mutableStateOf(0) }

    if (!isSelectGroup) {
        LazyColumn {
            items(timerGroups.size) { index ->
                TimerGroupWithoutRun(timerGroup = timerGroups[index], index = index, selectGroup = { index ->
                    isSelectGroup = true
                    chosenGroup = index
                })
            }
        }
    } else {
        println("navigate to add timers to chosen group")
        isSelectGroup = false
        navController.navigate("create_group/aaa")
    }


}

