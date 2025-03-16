package org.example.timercenter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.model.NotificationType
import org.example.timercenter.ui.model.SettingsModel
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.screen.*

@Composable
fun AppNavigation(timeAgoManager: TimeAgoManager, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.HOME.route) {

        composable(Screen.HOME.route) { backStackEntry ->
            val timerId = backStackEntry.arguments?.getString("timerId")?.toIntOrNull() ?: -1
            val groupId = backStackEntry.arguments?.getString("groupId")?.toIntOrNull() ?: -1

            HomeScreen(
                timerAgoManager = timeAgoManager,
                navController = navController,
                timers = TimerManager.timers,
                timerGroups = TimerManager.timerGroups,
                timerRestartId = timerId,
                timerGroupRestartId = groupId,
                onDeleteTimers = { timers ->
                    TimerManager.deleteTimers(timers)
                },
                onDeleteGroupTimers = { groups ->
                    TimerManager.deleteTimerGroups(groups)
                },
            )
        }

        composable(Screen.CREATE.route) { CreateScreen(navController = navController) }
        composable(Screen.HISTORY.route) {
            HistoryScreen(
                timers = TimerManager.timers,
                timerGroups = TimerManager.timerGroups,
                timerAgoManager = timeAgoManager,
                navController = navController
            )
        }
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
        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                navController = navController,
                settings = SettingsModel(NotificationType.SOUND),
                onSaveSettings = {})
        }
    }
}