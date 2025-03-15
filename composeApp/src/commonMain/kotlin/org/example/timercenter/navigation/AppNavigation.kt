package org.example.timercenter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.timercenter.ui.model.exampleTimerGroupsList
import org.example.timercenter.ui.model.exampleTimersList
import org.example.timercenter.ui.screen.*

@Composable
fun AppNavigation(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Screen.HOME.route) {

        composable(Screen.HOME.route) {
            HomeScreen(
                navController = navController,
                timers = exampleTimersList,
                timerGroups = exampleTimerGroupsList,
                onDeleteTimers = {},
                onEditTimer = {},
            )
        }

        composable(Screen.CREATE.route) { CreateScreen(navController = navController, onClose = {}) }
        composable(Screen.HISTORY.route) { HistoryScreen() }
        composable(Screen.CREATE_GROUP.route) { CreateTimerGroupScreen(timers = exampleTimersList, navController = navController) }
        composable(Screen.ADD_TO_GROUP.route) { AddTimersToGroupScreen(timerGroups = exampleTimerGroupsList, navController = navController) }
    }
}