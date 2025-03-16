package org.example.timercenter.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.timercenter.navigation.Screen
import org.example.timercenter.ui.PopupMessage
import org.example.timercenter.ui.model.TimerManager


@Composable
fun CreateScreen(navController: NavController) {

    // Получаем параметры из навигации
    val idString = navController.currentBackStackEntry?.arguments?.getString("id")
    val id = idString?.toIntOrNull()

    // Данные таймера
    val existingTimer = id?.let { TimerManager.findTimer(it) }
    var timerName by remember { mutableStateOf(existingTimer?.timerName ?: "") }
    var selectedHours by remember {
        mutableStateOf(
            ((existingTimer?.totalTime ?: 0L) / 3_600_000).toInt()
        )
    }
    var selectedMinutes by remember {
        mutableStateOf(
            (((existingTimer?.totalTime ?: 0L) % 3_600_000) / 60_000).toInt()
        )
    }
    var selectedSeconds by remember {
        mutableStateOf(
            (((existingTimer?.totalTime ?: 0L) % 60_000) / 1_000).toInt()
        )
    }
    var startImmediately by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Поле ввода имени таймера
        OutlinedTextField(
            value = timerName,
            onValueChange = { timerName = it },
            label = { Text("Название таймера", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Выбор времени
        TimePicker(
            selectedHours = selectedHours,
            selectedMinutes = selectedMinutes,
            selectedSeconds = selectedSeconds,
            onHoursChange = { selectedHours = it },
            onMinutesChange = { selectedMinutes = it },
            onSecondsChange = { selectedSeconds = it }
        )
        Spacer(Modifier.height(16.dp))

        // Тумблер мгновенного старта
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Начать таймер немедленно")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = startImmediately,
                onCheckedChange = { startImmediately = it },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.Blue)
            )
        }

        Spacer(Modifier.height(16.dp))

        if (id == null) {
            PartTimerGroups(navController = navController)
        }
        Spacer(modifier = Modifier.weight(1f))

        // Кнопка сохранения
        Button(
            onClick = {
                val totalMilliseconds =
                    (selectedHours * 3_600_000L) + (selectedMinutes * 60_000L) + (selectedSeconds * 1_000L)

                if (id == null) {
                    TimerManager.addTimer(timerName = timerName, totalTime = totalMilliseconds)
                    navController.navigate(Screen.HOME.route)
                } else {
                    // Редактирование существующего таймера
                    showPopup = true
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).height(48.dp),
        ) {
            Text("Сохранить таймер", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        // Popup при редактировании таймера
        if (showPopup) {
            PopupMessage(
                message = "Вы уверены, что хотите изменить этот таймер?",
                buttonText = "Изменить",
                onCancel = { showPopup = false },
                onConfirm = {
                    val totalMilliseconds =
                        (selectedHours * 3_600_000L) + (selectedMinutes * 60_000L) + (selectedSeconds * 1_000L)
                    TimerManager.editTimer(id!!, timerName, totalMilliseconds)
                    showPopup = false
                    navController.navigate(Screen.HOME.route)
                }
            )
        }
    }
}

@Composable
fun PartTimerGroups(navController: NavController) {
    // Раздел групп
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Группы таймеров", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(7.dp))
        GroupOption("Добавить таймеры в группу") { navController.navigate(Screen.ADD_TO_GROUP.route) }
        GroupOption("Создать новую группу") { navController.navigate(Screen.CREATE_GROUP.route) }


    }
}

// Composable-функция для выбора времени
@Composable
fun TimePicker(
    selectedHours: Int,
    selectedMinutes: Int,
    selectedSeconds: Int,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit,
    fontSize: Int = 48,
    showLabel: Boolean = true
) {
    if (showLabel) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "часы", fontSize = 14.sp, color = Color.Gray)
            Text(text = "минуты", fontSize = 14.sp, color = Color.Gray)
            Text(text = "секунды", fontSize = 14.sp, color = Color.Gray)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimePickerWheel(
            range = 0..23,
            selectedValue = selectedHours,
            onValueChange = onHoursChange,
            fontSize = fontSize
        )
        TimePickerDivider(fontSize = fontSize)
        TimePickerWheel(
            selectedValue = selectedMinutes,
            range = 0..59,
            onValueChange = onMinutesChange,
            fontSize = fontSize
        )
        TimePickerDivider(fontSize = fontSize)
        TimePickerWheel(
            selectedValue = selectedSeconds,
            range = 0..59,
            onValueChange = onSecondsChange,
            fontSize = fontSize
        )
    }
}

@Composable
fun TimePickerWheel(
    selectedValue: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    fontSize: Int = 48
) {
    // Умножаем список на 2 для создания "повторяющегося" эффекта
    val fullList = List(range.count() * 2) { range.elementAt(it % range.count()) }
    val initialIndex =
        fullList.size / 2 + range.indexOf(selectedValue) // Начинаем с выбранного элемента в центре

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val index = listState.firstVisibleItemIndex % range.count()
        onValueChange(range.elementAt(index))
    }

    // Колонка с выбором времени
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {


        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            modifier = Modifier.height(if (fontSize == 48) 100.dp else 75.dp)
        ) {
            itemsIndexed(fullList) { index, value ->
                val isSelected =
                    listState.firstVisibleItemIndex % range.count() == index % range.count()
                Text(
                    text = value.toString().padStart(2, '0'),
                    fontSize = fontSize.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Gray,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(if (fontSize == 48) 60.dp else 30.dp) // Устанавливаем высоту элемента
                        .wrapContentHeight(Alignment.CenterVertically) // Центрируем элементы
                )
            }
        }
    }
}


// Разделитель между колесами времени
@Composable
fun TimePickerDivider(fontSize: Int = 48) {
    Text(":", fontSize = fontSize.sp)
}

// Компонент группы
@Composable
fun GroupOption(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text)
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}
