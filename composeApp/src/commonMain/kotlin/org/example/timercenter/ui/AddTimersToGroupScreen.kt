package org.example.timercenter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun AddTimersToGroupScreen(timerGroups: List<TimerGroupUiModel>, navController: NavController) {
    var isSelectGroup by remember { mutableStateOf(false) }
    var chosenGroup by remember { mutableStateOf(0) }

    if (!isSelectGroup) {
        LazyColumn {
            items(timerGroups.size) { index ->
                TimerGroupWithoutRun(timerGroup = timerGroups[index], index = index, selectGroup = {
                    isSelectGroup = true
                    chosenGroup = it
                })
            }
        }
    } else {
        LazyColumn {

        }

    }


}

@Composable
fun TimerGroupWithoutRun(timerGroup: TimerGroupUiModel, index: Int, selectGroup: (Int) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().clickable { selectGroup(index) },
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = timerGroup.groupName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            // Кнопка сворачивания/разворачивания
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                )
            }
        }

        // Список таймеров, если группа развернута
        if (isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(300.dp) // Фиксированная высота списка
            ) {
                items(timerGroup.timers.size) { index ->
                    TimerAddToGroup(timer = timerGroup.timers[index], isSelected = false, onToggle = {})
                }
            }
        }

    }
}

