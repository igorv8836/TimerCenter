package org.example.timercenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.timercenter.ui_common.AppTheme
import org.example.timercenter.navigation.AppNavigation
import org.example.timercenter.ui.AppTopBar
import org.example.timercenter.ui.BottomNavigationBar
import org.example.timercenter.ui.bottomNavigationItems

@Composable
fun TimerApp() {
    AppTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = { AppTopBar(navController) }, // Верхняя панель
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    navController = navController
                )
            } // Нижняя панель
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation(navController)
            }
        }

    }
}