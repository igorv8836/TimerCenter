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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.orbit_mvi.compose.collectAsState
import com.example.orbit_mvi.compose.collectSideEffect
import org.example.timercenter.navigation.navigateToAddToGroup
import org.example.timercenter.navigation.navigateToCreateGroup
import org.example.timercenter.navigation.navigateToHome
import org.example.timercenter.ui.PopupMessage
import org.example.timercenter.ui.viewmodels.CreateTimerViewModel
import org.example.timercenter.ui.viewmodels.states.CreateTimerEffect
import org.example.timercenter.ui.viewmodels.states.CreateTimerEvent
import org.koin.compose.viewmodel.koinViewModel

/**
 * Экран создания таймера
 * @param navController Контроллер навигации
 * @param viewModel Модель представления экрана
 */
@Composable
fun CreateScreen(
    navController: NavController,
    viewModel: CreateTimerViewModel = koinViewModel(),
) {
    val state by viewModel.collectAsState()
    var snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    var showNameError by remember { mutableStateOf(false) }
    var showTimeError by remember { mutableStateOf(false) }

    viewModel.collectSideEffect {
        when (it) {
            is CreateTimerEffect.NavigateToHome -> navController.navigateToHome()
        }
    }

    LaunchedEffect(showNameError) {
        if (showNameError) {
            snackbarHostState.showSnackbar("Введите название таймера")
            showNameError = false
        }
    }

    LaunchedEffect(showTimeError) {
        if (showTimeError) {
            snackbarHostState.showSnackbar("Установите время таймера")
            showTimeError = false
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Поле ввода имени таймера
            OutlinedTextField(
                value = state.timerInfo.timerName,
                onValueChange = { viewModel.onEvent(CreateTimerEvent.SetName(it)) },
                label = { Text("Название таймера", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Выбор времени
            TimePicker(
                selectedHours = state.selectedHours,
                selectedMinutes = state.selectedMinutes,
                selectedSeconds = state.selectedSeconds,
                onHoursChange = { viewModel.onEvent(CreateTimerEvent.SetHours(it)) },
                onMinutesChange = { viewModel.onEvent(CreateTimerEvent.SetMinutes(it)) },
                onSecondsChange = { viewModel.onEvent(CreateTimerEvent.SetSeconds(it)) }
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
                    checked = state.startImmediately,
                    onCheckedChange = {
                        viewModel.onEvent(CreateTimerEvent.SetStartImmediately(it))
                    },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.Blue)
                )
            }

            Spacer(Modifier.height(16.dp))

            if (state.id == null) {
                PartTimerGroups(navController = navController)
            }
            Spacer(modifier = Modifier.weight(1f))

            // Кнопка сохранения
            Button(
                onClick = {
                    if (state.timerInfo.timerName.isBlank()) {
                        showNameError = true
                        return@Button
                    }
                    if (state.selectedHours == 0 && state.selectedMinutes == 0 && state.selectedSeconds == 0) {
                        showTimeError = true
                        return@Button
                    }
                    viewModel.onEvent(CreateTimerEvent.SaveTimer)
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).height(48.dp),
            ) {
                Text("Сохранить таймер", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Popup при редактировании таймера
            if (state.showPopup) {
                PopupMessage(
                    message = "Вы уверены, что хотите изменить этот таймер?",
                    buttonText = "Изменить",
                    onCancel = { viewModel.onEvent(CreateTimerEvent.SetShowPopup(false)) },
                    onConfirm = {
                        viewModel.onEvent(CreateTimerEvent.SaveTimer)
                    }
                )
            }
        }
    }
}

/**
 * Раздел групп таймеров
 * @param navController Контроллер навигации
 */
@Composable
fun PartTimerGroups(navController: NavController) {
    // Раздел групп
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Группы таймеров", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(7.dp))
        GroupOption("Добавить таймеры в группу") { navController.navigateToAddToGroup() }
        GroupOption("Создать новую группу") { navController.navigateToCreateGroup() }


    }
}

/**
 * Composable-функция для выбора времени
 * @param selectedHours Выбранные часы
 * @param selectedMinutes Выбранные минуты
 * @param selectedSeconds Выбранные секунды
 * @param onHoursChange Обработчик изменения часов
 * @param onMinutesChange Обработчик изменения минут
 * @param onSecondsChange Обработчик изменения секунд
 * @param fontSize Размер шрифта
 * @param showLabel Показывать ли метки
 */
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

/**
 * Колесо выбора времени
 * @param selectedValue Выбранное значение
 * @param range Диапазон значений
 * @param onValueChange Обработчик изменения значения
 * @param fontSize Размер шрифта
 */
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


/**
 * Разделитель между колесами времени
 * @param fontSize Размер шрифта
 */
@Composable
fun TimePickerDivider(fontSize: Int = 48) {
    Text(":", fontSize = fontSize.sp)
}

/**
 * Компонент группы
 * @param text Текст кнопки
 * @param onClick Обработчик нажатия
 */
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
