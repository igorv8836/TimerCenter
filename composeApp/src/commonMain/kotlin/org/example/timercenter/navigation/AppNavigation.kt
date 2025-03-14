package org.example.timercenter.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.timercenter.ui.AddTimersToGroupScreen
import org.example.timercenter.ui.CreateScreen
import org.example.timercenter.ui.CreateTimerGroupScreen
import org.example.timercenter.ui.HistoryScreen
import org.example.timercenter.ui.HomeScreen
import org.example.timercenter.ui.Screen
import org.example.timercenter.ui.createTimerGroupList
import org.example.timercenter.ui.createTimerList

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HOME.route) {
        composable(Screen.HOME.route) {
            HomeScreen(
                timers = createTimerList(10),
                timerGroups = createTimerGroupList(10)
            )
        }
        composable(Screen.CREATE.route) { CreateScreen(navController = navController, onClose = {}) }
        composable(Screen.HISTORY.route) { HistoryScreen() }
        composable(Screen.CREATE_GROUP.route) { CreateTimerGroupScreen(createTimerList(10), navController = navController) }
        composable(Screen.ADD_TO_GROUP.route) { AddTimersToGroupScreen(timerGroups = createTimerGroupList(10), navController = navController) }
    }
}