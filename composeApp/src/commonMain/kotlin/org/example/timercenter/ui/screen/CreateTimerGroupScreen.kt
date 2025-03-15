package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.timercenter.navigation.Screen
import org.example.timercenter.ui.PopupMessage
import org.example.timercenter.ui.item.TimerAddToGroup
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.model.exampleFindGroupTimer

@Composable
fun CreateTimerGroupScreen(timers: List<TimerUiModel>, navController: NavController) {


    val idString = navController.currentBackStackEntry?.arguments?.getString("id")
    val id = if (idString == "{id}") null else idString?.toInt()
    var name : String? = null
    if (id != null) {
        name = exampleFindGroupTimer(id = id)?.groupName
    }

    var option by remember { mutableIntStateOf(0) }
    var groupName by remember { mutableStateOf(name ?: "") }
    var selectedTimers by remember { mutableStateOf(setOf<TimerUiModel>()) } // Список выбранных таймеров

    var showPopup by remember { mutableStateOf(false)}

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
                onClick = {
                    if (name != null) navController.navigate(Screen.HOME.route)
                    else navController.navigate(Screen.CREATE.route) },
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
                onClick = {
                    if (name != null) {
                        showPopup = true
                    }
                    else {
                        navController.navigate(Screen.HOME.route)
                    }
                },
                modifier = Modifier.height(48.dp),
            ) {
                Text("Save", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        if (showPopup) {
            PopupMessage(
                message = "Are you sure you want to edit this timer group?",
                buttonText = "Edit",
                onCancel = { showPopup = false },
                onConfirm = {
                    showPopup = false
                    navController.navigate(Screen.HOME.route)
                })
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
