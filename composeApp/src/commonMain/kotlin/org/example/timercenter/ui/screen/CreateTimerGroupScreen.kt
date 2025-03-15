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
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerGroupUiModel
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.model.TimerUiModel


@Composable
fun CreateTimerGroupScreen(timers: List<TimerUiModel>, navController: NavController) {

    val idString = navController.currentBackStackEntry?.arguments?.getString("id")
    val id = idString?.toIntOrNull()
    val existingGroup = id?.let { TimerManager.findTimerGroup(it) }

    var groupName by remember { mutableStateOf(existingGroup?.groupName ?: "") }
    var option by remember { mutableStateOf(existingGroup?.groupType ?: GroupType.CONSISTENT) }
    var showPopup by remember { mutableStateOf(false) }

    // Если редактируем, берем выбранные таймеры из группы, иначе пустой список
    var selectedTimers by remember {
        mutableStateOf(existingGroup?.timers?.toSet() ?: emptySet())
    }

    // Разделяем таймеры: выбранные и остальные
    val (addedTimers, otherTimers) = timers.partition { it in selectedTimers }

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
        SingleChoiceSegmentedButton { selectedOption -> option = selectedOption }

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
            // Сначала добавленные таймеры (с крестиком)
            items(addedTimers.size) { index ->
                TimerAddToGroup(
                    timer = addedTimers[index],
                    isSelected = true,  // Они уже выбраны
                    onToggle = { selected ->
                        selectedTimers =
                            if (selected) selectedTimers + addedTimers[index] else selectedTimers - addedTimers[index]
                    }
                )
            }
            // Затем остальные таймеры (с плюсиком)
            items(otherTimers.size) { index ->
                TimerAddToGroup(
                    timer = otherTimers[index],
                    isSelected = false,  // Они еще не выбраны
                    onToggle = { selected ->
                        selectedTimers =
                            if (selected) selectedTimers + otherTimers[index] else selectedTimers - otherTimers[index]
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
                onClick = { navController.navigate(Screen.HOME.route) },
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
                    if (id != null) showPopup = true
                    else {
                        // Если не редактируем, создаем новую группу
                        val newGroup = TimerGroupUiModel(
                            id = TimerManager.timers.size + 1,  // Уникальный id для новой группы
                            groupName = groupName,
                            groupType = option,
                            timers = selectedTimers.toList()
                        )
                        TimerManager.addTimerGroup(
                            groupName = groupName,
                            groupType = option,
                            timers = selectedTimers.toList()
                        ) // Добавляем группу в список
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
                    TimerManager.editTimerGroup(
                        id = id!!,
                        newName = groupName,
                        newType = option,
                        newTimers = selectedTimers.toList()
                    )
                    showPopup = false
                    navController.navigate(Screen.HOME.route)
                }
            )
        }
    }
}


@Composable
fun SingleChoiceSegmentedButton(onOptionChange: (GroupType) -> Unit) {
    var selectedOption by remember { mutableStateOf(GroupType.CONSISTENT) } // Используем GroupType вместо индекса
    val options = listOf(GroupType.CONSISTENT, GroupType.PARALLEL, GroupType.DELAY) // Список типов GroupType

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, groupType ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedOption = groupType // Обновляем выбранный тип
                    onOptionChange(groupType) // Передаем GroupType в onOptionChange
                },
                selected = groupType == selectedOption,
                label = {
                    Text(
                        when (groupType) {
                            GroupType.CONSISTENT -> "Послед-ный"
                            GroupType.PARALLEL -> "Парал-ный"
                            GroupType.DELAY -> "С задержкой"
                        },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                },
            )
        }
    }
}