package org.example.timercenter.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun PopupMessage(
    message: String,
    buttonText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    // Создаем состояние для показа/скрытия диалога
    var showDialog by remember { mutableStateOf(true) }

    // Настройка диалога
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },  // Закрытие при клике вне диалога
            title = {
                Text(text = message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onConfirm() // Вызываем функцию при подтверждении
                    }
                ) {
                    Text(buttonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onCancel() // Вызываем функцию при отмене
                    }
                ) {
                    Text("Отмена")
                }
            },
        )
    }
}
