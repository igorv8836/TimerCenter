package org.example.timercenter.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.timercenter.ui.CreateScreen
import org.example.timercenter.ui.HistoryScreen
import org.example.timercenter.ui.HomeScreen
import org.example.timercenter.ui.createTimerList

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(timers = createTimerList(50)) }
        composable("create") { CreateScreen() }
        composable("history") { HistoryScreen() }
    }
}

//@Composable
//fun AppNavigation(navController: NavHostController) {
//
//    NavHost(navController = navController, startDestination = HomeScreenRoute) {
//        composable<HomeScreenRoute> {
//            HomeScreen(timers = createTimerList(50))
//        }
//
//
//        composable<CreateScreenRoute> {
//            CreateScreen()
//        }
//
//        composable<HistoryScreenRoute> {
//            HistoryScreen()
//        }
//
//    }
//}