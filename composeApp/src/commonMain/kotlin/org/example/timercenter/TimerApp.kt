package org.example.timercenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.timercenter.ui_common.AppTheme
import org.example.timercenter.navigation.AppNavigation
import org.example.timercenter.navigation.HomeScreenRoute
import org.example.timercenter.navigation.navigateToSettings
import org.example.timercenter.ui.AppTopBar
import org.example.timercenter.ui.BottomNavigationBar

/**
 * Главный компонент приложения TimerCenter
 * @param timeAgoManager Менеджер для форматирования времени
 */
@Composable
fun TimerApp(timeAgoManager: TimeAgoManager) {
    AppTheme {
        val navController = rememberNavController()
        val isHomeScreen = navController.currentBackStackEntryAsState().value?.destination
            ?.hasRoute<HomeScreenRoute>() ?: false

        Scaffold(
            topBar = {
                if (!isHomeScreen) {
                    AppTopBar(navController) {
                        navController.navigateToSettings()
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation(timeAgoManager = timeAgoManager, navController = navController)
            }
        }
    }
}