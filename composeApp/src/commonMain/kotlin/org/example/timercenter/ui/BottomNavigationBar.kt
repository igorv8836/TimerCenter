package org.example.timercenter.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.timercenter.navigation.CreateScreenRoute
import org.example.timercenter.navigation.HistoryScreenRoute
import org.example.timercenter.navigation.HomeScreenRoute

/**
 * Элемент нижней навигационной панели
 * @param title Название элемента
 * @param selectedIcon Иконка для выбранного состояния
 * @param unselectedIcon Иконка для невыбранного состояния
 * @param route Маршрут навигации
 */
data class BottomNavItem<T : Any>(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: T
)

/**
 * Список элементов нижней навигационной панели
 */
val bottomNavigationItems = listOf(
    BottomNavItem(
        title = "Главная",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = HomeScreenRoute(-1, -1),
    ),
    BottomNavItem(
        title = "Создать",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add,
        route = CreateScreenRoute(-1),
    ),
    BottomNavItem(
        title = "История",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History,
        route = HistoryScreenRoute,
    ),
)

/**
 * Нижняя навигационная панель приложения
 * @param navController Контроллер навигации
 */
@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val selectedItemIndex = bottomNavigationItems.indexOfFirst { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
    }

    NavigationBar {
        bottomNavigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItemIndex,
                onClick = {
                    if (currentDestination?.hasRoute(item.route::class) == true) return@NavigationBarItem
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                },
            )
        }
    }
}
