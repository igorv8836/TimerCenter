package org.example.timercenter.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.timercenter.ui.model.TimerGroupUiModel

@Composable
fun TimerGroupWithoutRun(timerGroup: TimerGroupUiModel, id: Int, selectGroup: (Int) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.Companion.fillMaxWidth().clickable { selectGroup(id) },
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = timerGroup.groupName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Companion.Bold,
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
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp)
//                    .height(300.dp) // Фиксированная высота списка
            ) {
                items(timerGroup.timers.size) { index ->
                    TimerWithoutAll(timer = timerGroup.timers[index])
//                    TimerAddToGroup(timer = timerGroup.timers[index], isSelected = false, onToggle = {})
                }
            }
        }

    }
}