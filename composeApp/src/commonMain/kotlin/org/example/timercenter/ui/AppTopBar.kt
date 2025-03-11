package org.example.timercenter.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.timercenter.navigation.CreateScreenRoute
import org.example.timercenter.navigation.HistoryScreenRoute
import org.example.timercenter.navigation.HomeScreenRoute

private const val TAG = "AppTopBar"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavController) {

    // Отслеживаем текущий маршрут, чтобы перерисовывать TopAppBar при изменении маршрута
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    println("[$TAG] Текущий маршрут: ${navController.currentBackStackEntry?.destination?.route}")


    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    "home" -> "Главная"
                    "create" -> "Создать"
                    "history" -> "История"
                    else -> "Таймеры"
                },
                fontSize = 20.sp
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}