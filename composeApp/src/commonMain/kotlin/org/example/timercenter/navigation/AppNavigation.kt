package org.example.timercenter.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.timercenter.TestContent
import org.example.timercenter.ui.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = StartScreenRoute) {
        composable<StartScreenRoute> {
            TestContent(navController = navController
            )
        }
        composable<MainScreenRoute> {
            Text(text = "Main Screen")
        }
        composable<HomeScreenRoute> {
            HomeScreen()
        }
    }
}