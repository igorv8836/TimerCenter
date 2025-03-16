package org.example.timercenter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.model.TimerManager
import org.example.timercenter.ui.screen.AddTimersToGroupScreen
import org.example.timercenter.ui.screen.CreateScreen
import org.example.timercenter.ui.screen.CreateTimerGroupScreen
import org.example.timercenter.ui.screen.HistoryScreen
import org.example.timercenter.ui.screen.HomeScreen
import org.example.timercenter.ui.screen.SettingsScreen
import org.example.timercenter.ui.viewmodels.HomeViewModel
import org.example.timercenter.ui.viewmodels.states.HomeEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(timeAgoManager: TimeAgoManager, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.HOME.route) {

        composable(Screen.HOME.route) { backStackEntry ->
            val timerId = backStackEntry.arguments?.getString("timerId")?.toIntOrNull() ?: -1
            val groupId = backStackEntry.arguments?.getString("groupId")?.toIntOrNull() ?: -1

            val homeViewModel: HomeViewModel = koinViewModel()

            // Если переданы аргументы для рестарта, отправляем соответствующие события в ViewModel.
            LaunchedEffect(timerId) {
                if (timerId != -1) {
                    homeViewModel.onEvent(HomeEvent.SetTimerRestart(timerId))
                }
            }
            LaunchedEffect(groupId) {
                if (groupId != -1) {
                    homeViewModel.onEvent(HomeEvent.SetTimerGroupRestart(groupId))
                }
            }

            HomeScreen(
                timerAgoManager = timeAgoManager,
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(Screen.CREATE.route) { CreateScreen(navController = navController) }
        composable(Screen.HISTORY.route) {
            HistoryScreen(
                timerAgoManager = timeAgoManager,
                navController = navController,
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
            )
        }
    }
}