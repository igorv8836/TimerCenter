package org.example.timercenter.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.timercenter.navigation.Screen
import org.example.timercenter.ui.model.NotificationType
import org.example.timercenter.ui.model.SettingsModel

@Composable
fun SettingsScreen(
    navController: NavController,
    onSaveSettings: (SettingsModel) -> Unit,
    settings: SettingsModel
) {
    var selectedNotificationType by remember { mutableStateOf(settings.notificationType) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Тип уведомлений")
        Spacer(modifier = Modifier.height(12.dp))

        // Компонент для выбора типа уведомлений с помощью сегментированных кнопок
        SingleChoiceSegmentedButton(
            onOptionChange = { selectedOption ->
                selectedNotificationType = selectedOption
            },
            selectedOption = selectedNotificationType
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Сохраняем выбранные настройки
                onSaveSettings(SettingsModel(selectedNotificationType))
                navController.navigate(Screen.HOME.route)
            },
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