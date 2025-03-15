package org.example.timercenter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.screen.*

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.HOME.route) {

        composable(Screen.HOME.route) {
            HomeScreen(
                navController = navController,
                timers = TimerManager.timers,
                timerGroups = TimerManager.timerGroups,
                onDeleteTimers = { timers ->
                    TimerManager.deleteTimers(timers)
                },
                onDeleteGroupTimers = { groups ->
                    TimerManager.deleteTimerGroups(groups)
                },
                onEditTimer = {},
            )
        }

        composable(Screen.CREATE.route) { CreateScreen(navController = navController, onClose = {}) }
        composable(Screen.HISTORY.route) { HistoryScreen() }
        composable(Screen.CREATE_GROUP.route) {
            CreateTimerGroupScreen(
                timers = TimerManager.timers,
                navController = navController
            )
        }
        composable(Screen.ADD_TO_GROUP.route) {
            AddTimersToGroupScreen(
                timerGroups = TimerManager.timerGroups,
                navController = navController
            )
        }
    }
}