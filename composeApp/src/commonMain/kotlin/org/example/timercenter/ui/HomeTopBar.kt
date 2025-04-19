package org.example.timercenter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * Верхняя панель навигации для главного экрана приложения
 * Реализует функционал управления таймерами с возможностью выбора, редактирования и удаления
 * @param onSettingsClick Обработчик нажатия на кнопку настроек
 * @param isSelectionMode Флаг режима выбора элементов
 * @param selectCount Количество выбранных элементов
 * @param isEditEnabled Флаг доступности кнопки редактирования
 * @param onDeleteClick Обработчик нажатия на кнопку удаления
 * @param onEditClick Обработчик нажатия на кнопку редактирования
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSettingsClick: () -> Unit,
    isSelectionMode: Boolean,
    selectCount: Int,
    isEditEnabled: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = if (isSelectionMode) "Выбрано: $selectCount" else "Таймеры",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            if (isSelectionMode) {
                IconButton(
                    onClick = onEditClick,
                    enabled = isEditEnabled
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = if (isEditEnabled) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}