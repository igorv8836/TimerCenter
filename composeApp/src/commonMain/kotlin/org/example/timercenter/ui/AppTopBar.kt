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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.timercenter.navigation.CreateScreenRoute
import org.example.timercenter.navigation.HistoryScreenRoute
import org.example.timercenter.navigation.HomeScreenRoute
import org.jetbrains.compose.resources.painterResource

private const val TAG = "AppTopBar"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController, onSettingsClick: () -> Unit
) {

    // Отслеживаем текущий маршрут, чтобы перерисовывать TopAppBar при изменении маршрута
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    println("[$TAG] Текущий маршрут: ${navController.currentBackStackEntry?.destination?.route}")


    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    Screen.HOME.route -> Screen.HOME.title
                    Screen.CREATE.route -> Screen.CREATE.title
                    Screen.HISTORY.route -> Screen.HISTORY.title
                    else -> "Таймеры"
                },
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground, // Цвет текста
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = onSettingsClick) { // Кнопка для настроек
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
            }
        },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}