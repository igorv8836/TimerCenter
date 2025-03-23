package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import androidx.navigation.NavController
import com.example.orbit_mvi.compose.collectAsState
import com.example.orbit_mvi.compose.collectSideEffect
import org.example.timercenter.navigation.Screen
import org.example.timercenter.ui.PopupMessage
import org.example.timercenter.ui.item.TimerAddToGroup
import org.example.timercenter.ui.model.GroupType
import org.example.timercenter.ui.model.TimerUiModel
import org.example.timercenter.ui.viewmodels.CreateTimerGroupViewModel
import org.example.timercenter.ui.viewmodels.states.CreateTimerEffect
import org.example.timercenter.ui.viewmodels.states.CreateTimerEvent
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupEffect
import org.example.timercenter.ui.viewmodels.states.CreateTimerGroupEvent
import org.koin.compose.viewmodel.koinViewModel


private const val TAG = "CreateTimerGroupScreen"

@Composable
fun CreateTimerGroupScreen(
    navController: NavController,
    viewModel: CreateTimerGroupViewModel = koinViewModel()
) {

    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            is CreateTimerGroupEffect.NavigateToHome -> navController.navigate(Screen.HOME.route)
        }
    }

    viewModel.onEvent(
        CreateTimerGroupEvent.SetTimerGroupId(
            navController.currentBackStackEntry?.arguments?.getString("id")?.toIntOrNull()
        )
    )

//    val idString = navController.currentBackStackEntry?.arguments?.getString("id")
//    val id = idString?.toIntOrNull()
//    val existingGroup = id?.let { TimerManager.findTimerGroup(it) }
//    println("$TAG existingGroup is $existingGroup")
//    var groupName by remember { mutableStateOf(existingGroup?.groupName ?: "") }
//    var option by remember { mutableStateOf(existingGroup?.groupType ?: GroupType.CONSISTENT) }
//    println("$TAG option is $option")
//    var showPopup by remember { mutableStateOf(false) }
//    val isDelayMode = remember(option) { option == GroupType.DELAY }
//    println("$TAG idDelayMode $isDelayMode")
//    // Если редактируем, берем выбранные таймеры из группы, иначе пустой список
//    var selectedTimers by remember {
//        mutableStateOf(existingGroup?.timers?.toSet() ?: emptySet())
//    }
//
//    var selectedHours by remember { mutableStateOf(0) }
//    var selectedMinutes by remember { mutableStateOf(0) }
//    var selectedSeconds by remember { mutableStateOf(0) }
//
//    // Разделяем таймеры: выбранные и остальные
//    val (addedTimers, otherTimers) = timers.partition { it in selectedTimers }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        // Поле ввода имени группы
        OutlinedTextField(
            value = state.timerGroupInfo.groupName,
            onValueChange = { viewModel.onEvent(CreateTimerGroupEvent.SetName(it)) },
            label = { Text("Name your group") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Тип группы", modifier = Modifier.padding(vertical = 16.dp))
        SingleChoiceSegmentedButton(selectedOption = state.timerGroupInfo.groupType) { selectedOption ->
            viewModel.onEvent(CreateTimerGroupEvent.SetGroupType(selectedOption))
        }

        if (state.timerGroupInfo.groupType == GroupType.DELAY) {
            Spacer(Modifier.height(16.dp))
            // Выбор времени
            TimePicker(
                selectedHours = state.delaySelectedHours,
                selectedMinutes = state.delaySelectedMinutes,
                selectedSeconds = state.delaySelectedSeconds,
                onHoursChange = { viewModel.onEvent(CreateTimerGroupEvent.SetDelayHours(it)) },
                onMinutesChange = { viewModel.onEvent(CreateTimerGroupEvent.SetDelayMinutes(it)) },
                onSecondsChange = { viewModel.onEvent(CreateTimerGroupEvent.SetDelaySeconds(it)) },
                showLabel = false,
            )
        }

        // Заголовок с количеством выбранных таймеров
        Text(
            text = "Добавить таймеры (${state.timerGroupInfo.timers.size})",
            modifier = Modifier.padding(top = 20.dp, bottom = 4.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            // Сначала добавленные таймеры (с крестиком)
            items(state.addedTimers.size) { index ->
                TimerAddToGroup(
                    timer = state.addedTimers[index],
                    isSelected = true,  // Они уже выбраны
                    onToggle = { selected ->
                        if (selected) viewModel.onEvent(CreateTimerGroupEvent.AddTimerToGroup(state.addedTimers[index]))
                        else viewModel.onEvent(CreateTimerGroupEvent.DeleteTimerFromGroup(state.addedTimers[index]))
                    }
                )
            }
            // Затем остальные таймеры (с плюсиком)
            items(state.allTimers.filter { timer -> !state.addedTimers.contains(timer) }.size) { index ->
                TimerAddToGroup(
                    timer = state.allTimers.filter { timer -> !state.addedTimers.contains(timer) }[index],
                    isSelected = false,  // Они еще не выбраны
                    onToggle = { selected ->
                        if (selected) viewModel.onEvent(CreateTimerGroupEvent.AddTimerToGroup(state.allTimers.filter { timer ->
                            !state.addedTimers.contains(
                                timer
                            )
                        }[index]))
                        else viewModel.onEvent(CreateTimerGroupEvent.DeleteTimerFromGroup(state.allTimers.filter { timer ->
                            !state.addedTimers.contains(
                                timer
                            )
                        }[index]))
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
                Text("Отмена", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = {
                    if (state.id != null) viewModel.onEvent(CreateTimerGroupEvent.SetShowPopup(true))
                    else {
                        viewModel.onEvent(CreateTimerGroupEvent.SaveTimerGroup)
                    }
                },
                modifier = Modifier.height(48.dp),
            ) {
                Text("Сохранить", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (state.showPopup) {
            PopupMessage(
                message = "Вы уверены, что хотите изменить эту группу таймеров?",
                buttonText = "Изменить",
                onCancel = { viewModel.onEvent(CreateTimerGroupEvent.SetShowPopup(false)) },
                onConfirm = {
                    viewModel.onEvent(CreateTimerGroupEvent.SaveTimerGroup)
//                    if (isDelayMode) {
//
//                        TimerManager.editTimerGroup(
//                            id = id!!,
//                            newName = groupName,
//                            newType = option,
//                            delayTime = (selectedHours * 3600000).toLong() + (selectedMinutes * 60000) + (selectedSeconds * 1000),
//                            newTimers = selectedTimers.toList()
//                        )
//                    } else {
//                        TimerManager.editTimerGroup(
//                            id = id!!,
//                            newName = groupName,
//                            newType = option,
//                            newTimers = selectedTimers.toList()
//                        )
//                    }
                }
            )
        }
    }
}


@Composable
fun SingleChoiceSegmentedButton(selectedOption: GroupType, onOptionChange: (GroupType) -> Unit) {
    val options =
        listOf(GroupType.CONSISTENT, GroupType.PARALLEL, GroupType.DELAY) // Список типов GroupType

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, groupType ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
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