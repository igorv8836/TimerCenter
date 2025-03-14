package org.example.timercenter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
fun CreateScreen(navController: NavController, onClose: () -> Unit) {
    var timerName by remember { mutableStateOf("") }
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var selectedSeconds by remember { mutableStateOf(0) }
    var startImmediately by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Поле ввода имени таймера
        OutlinedTextField(
            value = timerName,
            onValueChange = { timerName = it },
            label = { Text("Name your timer", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Выбор времени

        // Новый компонент выбора времени
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
            Text("Start the timer immediately", color = Color.White)
            Spacer(Modifier.weight(1f))
            Switch(
                checked = startImmediately,
                onCheckedChange = { startImmediately = it },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.Blue)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Раздел групп
        Text("Timer Groups", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(7.dp))
        GroupOption("Add timers to existing group") {navController.navigate(Screen.ADD_TO_GROUP.route)}
        GroupOption("Create new group") { navController.navigate(Screen.CREATE_GROUP.route)}

        Spacer(Modifier.weight(1f))

        // Кнопка сохранения
        Button(
            onClick = {
            /* Логика сохранения */
                navController.navigate(Screen.HOME.route)

            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).height(48.dp),
        ) {
            Text("Save Timer", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
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
    onSecondsChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "hours", fontSize = 14.sp, color = Color.Gray)
        Text(text = "min", fontSize = 14.sp, color = Color.Gray)
        Text(text = "sec", fontSize = 14.sp, color = Color.Gray)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimePickerWheel(range = 0..23, selectedValue = selectedHours, label = "hours", onValueChange = onHoursChange)
        TimePickerDivider()
        TimePickerWheel(selectedValue = selectedMinutes, range = 0..59, label = "min", onValueChange = onMinutesChange)
        TimePickerDivider()
        TimePickerWheel(selectedValue = selectedSeconds, range = 0..59, label = "sec", onValueChange = onSecondsChange)
    }
}

@Composable
fun TimePickerWheel(
    label: String,
    selectedValue: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit
) {
    // Умножаем список на 2 для создания "повторяющегося" эффекта
    val fullList = List(range.count() * 2) { range.elementAt(it % range.count()) }
    val initialIndex = fullList.size / 2 + range.indexOf(selectedValue) // Начинаем с выбранного элемента в центре

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
            modifier = Modifier.height(100.dp)
        ) {
            itemsIndexed(fullList) { index, value ->
                val isSelected = listState.firstVisibleItemIndex % range.count() == index % range.count()
                Text(
                    text = value.toString().padStart(2, '0'),
                    fontSize = 48.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else Color.Gray,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(60.dp) // Устанавливаем высоту элемента
                        .wrapContentHeight(Alignment.CenterVertically) // Центрируем элементы
                )
            }
        }
    }
}


// Разделитель между колесами времени
@Composable
fun TimePickerDivider() {
    Text(":", fontSize = 48.sp)
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
        Text(text, color = Color.White)
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
    }
}
