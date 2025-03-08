package org.example.timercenter.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.timercenter.TestContent

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = StartScreenRoute) {
        composable<StartScreenRoute> {
            TestContent()
        }

        composable<MainScreenRoute> {
            Text(text = "Main Screen")
        }
    }
}