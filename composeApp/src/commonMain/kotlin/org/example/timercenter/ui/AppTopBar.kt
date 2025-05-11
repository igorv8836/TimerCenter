package org.example.timercenter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.timercenter.navigation.AddToGroupScreenRoute
import org.example.timercenter.navigation.CreateGroupScreenRoute
import org.example.timercenter.navigation.CreateScreenRoute
import org.example.timercenter.navigation.HistoryScreenRoute
import org.example.timercenter.navigation.HomeScreenRoute
import org.example.timercenter.navigation.SettingsScreenRoute

/**
 * Получает название текущего экрана для отображения в заголовке
 * @return Название текущего экрана
 */
@Composable
fun NavController.getRouteName(): String {
    val destination = currentBackStackEntryAsState().value?.destination ?: return "Таймеры"
    return when {
        destination.hasRoute<HomeScreenRoute>() -> "Главная"
        destination.hasRoute<CreateScreenRoute>() -> "Создать"
        destination.hasRoute<CreateGroupScreenRoute>() -> "Создать группу"
        destination.hasRoute<AddToGroupScreenRoute>() -> "Выберите группу"
        destination.hasRoute<HistoryScreenRoute>() -> "История"
        destination.hasRoute<SettingsScreenRoute>() -> "Настройки"
        else -> "Таймеры"
    }
}

/**
 * Верхняя панель приложения
 * Отображает название текущего экрана и кнопку настроек
 * @param navController Контроллер навигации
 * @param onSettingsClick Обработчик нажатия на кнопку настроек
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController, onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = navController.getRouteName(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            if (navController.currentDestination?.hasRoute<HomeScreenRoute>() == true) {
                IconButton(onClick = onSettingsClick) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                }
            }
        },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}