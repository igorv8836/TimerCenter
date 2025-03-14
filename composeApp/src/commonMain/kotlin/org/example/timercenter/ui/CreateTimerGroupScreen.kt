package org.example.timercenter.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CreateTimerGroupScreen(timers: List<TimerUiModel>, navController: NavController) {
    var option by remember { mutableIntStateOf(0) }
    var groupName by remember { mutableStateOf("") }
    var selectedTimers by remember { mutableStateOf(setOf<TimerUiModel>()) } // Список выбранных таймеров

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        // Поле ввода имени группы
        OutlinedTextField(
            value = groupName,
            onValueChange = { groupName = it },
            label = { Text("Name your group") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Group Type", modifier = Modifier.padding(vertical = 16.dp))
        SingleChoiceSegmentedButton({ index ->
            option = index
        })

        // Заголовок с количеством выбранных таймеров
        Text(
            text = "Add Timers (${selectedTimers.size})",
            modifier = Modifier.padding(top = 20.dp, bottom = 4.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            items(timers.size) { index ->
                TimerAddToGroup(
                    timer = timers[index],
                    isSelected = timers[index] in selectedTimers,
                    onToggle = { selected ->
                        selectedTimers = if (selected) {
                            selectedTimers + timers[index]
                        } else {
                            selectedTimers - timers[index]
                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { navController.navigate(Screen.CREATE.route) },
                modifier = Modifier.height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Cancel", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = { navController.navigate(Screen.HOME.route) },
                modifier = Modifier.height(48.dp),
            ) {
                Text("Save and Start", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TimerAddToGroup(timer: TimerUiModel, isSelected: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = timer.timerName,
            fontSize = 16.sp,
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = formatTime(timer.totalTime),
            fontSize = 16.sp,
        )
        Spacer(Modifier.width(8.dp))
        IconButton(onClick = { onToggle(!isSelected) }) {
            Icon(
                imageVector = if (isSelected) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (isSelected) "Remove timer" else "Add timer",
                tint = if (isSelected) Color.Red.copy(alpha = 0.7f) else Color.Green.copy(alpha = 0.7f)
            )
        }
    }
}


@Composable
fun SingleChoiceSegmentedButton(onOptionChange: (Int) -> Unit) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Послед-ный", "Парал-ный", "С задержкой")

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onOptionChange(index)
                },
                selected = index == selectedIndex,
                label = { Text(label, modifier = Modifier.padding(horizontal = 2.dp)) },
            )
        }
    }
}
