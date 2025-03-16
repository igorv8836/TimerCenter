package org.example.timercenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.timercenter.ui_common.AppTheme
import org.example.timercenter.navigation.AppNavigation
import org.example.timercenter.ui.AppTopBar
import org.example.timercenter.ui.BottomNavigationBar
import org.example.timercenter.navigation.Screen
import org.example.timercenter.ui.bottomNavigationItems

@Composable
fun TimerApp(timeAgoManager: TimeAgoManager) {
    AppTheme {
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val showTopBar = currentRoute != Screen.HOME.route // Показывать, если не Home

        Scaffold(
            topBar = { if (showTopBar) {
                AppTopBar(navController) {
                    navController.navigate("settings")
                }
            }
            }, // Верхняя панель
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    navController = navController
                )
            } // Нижняя панель
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation(timeAgoManager = timeAgoManager, navController = navController)
            }
        }

    }
}