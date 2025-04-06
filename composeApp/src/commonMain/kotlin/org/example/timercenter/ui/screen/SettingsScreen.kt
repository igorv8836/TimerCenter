package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.orbit_mvi.compose.collectAsState
import org.example.timercenter.navigation.navigateToHome
import org.example.timercenter.ui.model.NotificationType
import org.example.timercenter.ui.viewmodels.SettingsViewModel
import org.example.timercenter.ui.viewmodels.states.SettingsEvent
import org.example.timercenter.ui.viewmodels.states.SettingsState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.collectAsState()

    when (state) {
        is SettingsState.Success -> {
            SettingsScreenContent(
                state = state as SettingsState.Success,
                onEvent = viewModel::onEvent,
                navigateToHome = {
                    navController.navigateToHome()
                }
            )
        }

        is SettingsState.Error -> {
            ErrorScreen(
                errorMessage = (state as SettingsState.Error).text,
            )
        }

        SettingsState.Loading -> { LoadingScreen() }
    }
}

@Composable
fun SettingsScreenContent(
    state: SettingsState.Success,
    onEvent: (SettingsEvent) -> Unit,
    navigateToHome: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Тип уведомлений")
        Spacer(modifier = Modifier.height(12.dp))

        // Компонент для выбора типа уведомлений с помощью сегментированных кнопок
        SingleChoiceSegmentedButton(
            onOptionChange = {
                onEvent(SettingsEvent.ChangeNotification(it))
            },
            selectedOption = state.data.notificationType
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = navigateToHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить настройки")
        }
    }
}

@Composable
fun SingleChoiceSegmentedButton(
    onOptionChange: (NotificationType) -> Unit,
    selectedOption: NotificationType
) {
    val options = NotificationType.entries.toTypedArray()

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, notificationType ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    onOptionChange(notificationType) // Передаем выбранный тип уведомления
                },
                selected = notificationType == selectedOption,
                label = {
                    Text(
                        when (notificationType) {
                            NotificationType.SOUND -> "Звук"
                            NotificationType.VIBRATION -> "Вибрация"
                            NotificationType.TEXT -> "Текст"
                        },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                },
            )
        }
    }
}