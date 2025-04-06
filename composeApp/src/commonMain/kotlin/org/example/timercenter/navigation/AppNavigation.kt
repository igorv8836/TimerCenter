package org.example.timercenter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.timercenter.TimeAgoManager
import org.example.timercenter.ui.screen.AddTimersToGroupScreen
import org.example.timercenter.ui.screen.CreateScreen
import org.example.timercenter.ui.screen.CreateTimerGroupScreen
import org.example.timercenter.ui.screen.HistoryScreen
import org.example.timercenter.ui.screen.HomeScreen
import org.example.timercenter.ui.screen.SettingsScreen
import org.example.timercenter.ui.viewmodels.CreateTimerViewModel
import org.example.timercenter.ui.viewmodels.HomeViewModel
import org.example.timercenter.ui.viewmodels.states.CreateTimerEvent
import org.example.timercenter.ui.viewmodels.states.HomeEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(timeAgoManager: TimeAgoManager, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute(
            timerId = -1,
            groupId = -1,
        )
    ) {
        composable<HomeScreenRoute> {
            val args = it.toRoute<HomeScreenRoute>()
            val timerId = args.timerId
            val groupId = args.groupId

            val homeViewModel: HomeViewModel = koinViewModel()

            // Если переданы аргументы для рестарта, отправляем соответствующие события в ViewModel.
            LaunchedEffect(timerId) {
                if (timerId != null) {
                    homeViewModel.onEvent(HomeEvent.SetTimerRestart(timerId))
                }
            }
            LaunchedEffect(groupId) {
                if (groupId != null) {
                    homeViewModel.onEvent(HomeEvent.SetTimerGroupRestart(groupId))
                }
            }

            HomeScreen(
                timerAgoManager = timeAgoManager,
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable<CreateScreenRoute> {
            val viewModel: CreateTimerViewModel = koinViewModel()

            viewModel.onEvent(
                CreateTimerEvent.SetTimerId(
                    id = it.toRoute<CreateScreenRoute>().id,
                )
            )

            CreateScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable<HistoryScreenRoute> {
            HistoryScreen(
                timerAgoManager = timeAgoManager,
                navController = navController,
            )
        }

        composable<CreateGroupScreenRoute> {
            CreateTimerGroupScreen(
                navController = navController
            )
        }

        composable<AddToGroupScreenRoute> {
            AddTimersToGroupScreen(
                navController = navController
            )
        }

        composable<SettingsScreenRoute> {
            SettingsScreen(
                navController = navController,
            )
        }
    }
}