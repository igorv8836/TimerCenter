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

/**
 * Основной компонент навигации приложения
 * определяет маршруты и экраны приложения
 * @param timeAgoManager Менеджер для форматирования времени
 * @param navController Контроллер навигации
 */
@Composable
fun AppNavigation(timeAgoManager: TimeAgoManager, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute(
            timerId = -1,
            groupId = -1,
        )
    ) {
        /**
         * Маршрут главного экрана
         * обрабатывает создание таймеров и групп из истории
         */
        composable<HomeScreenRoute> {
            val args = it.toRoute<HomeScreenRoute>()
            val timerId = args.timerId
            val groupId = args.groupId

            val homeViewModel: HomeViewModel = koinViewModel()

            LaunchedEffect(timerId) {
                if (timerId != null) {
                    homeViewModel.onEvent(HomeEvent.CreateTimerFromHistory(timerId))
                }
            }
            LaunchedEffect(groupId) {
                if (groupId != null) {
                    homeViewModel.onEvent(HomeEvent.CreateTimerGroupFromHistory(groupId))
                }
            }

            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        /**
         * Маршрут экрана создания/редактирования таймера
         */
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

        /**
         * Маршрут экрана истории таймеров
         */
        composable<HistoryScreenRoute> {
            HistoryScreen(
                timerAgoManager = timeAgoManager,
                navController = navController,
            )
        }

        /**
         * Маршрут экрана создания/редактирования группы
         */
        composable<CreateGroupScreenRoute> {
            val args = it.toRoute<CreateGroupScreenRoute>()
            CreateTimerGroupScreen(
                navController = navController,
                groupId = args.id
            )
        }

        /**
         * Маршрут экрана добавления таймеров в группу
         */
        composable<AddToGroupScreenRoute> {
            AddTimersToGroupScreen(
                navController = navController
            )
        }

        /**
         * Маршрут экрана настроек
         */
        composable<SettingsScreenRoute> {
            SettingsScreen(
                navController = navController,
            )
        }
    }
}