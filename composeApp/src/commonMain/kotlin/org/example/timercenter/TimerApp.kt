package org.example.timercenter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.timercenter.ui_common.AppTheme
import org.example.timercenter.navigation.AppNavigation
import org.example.timercenter.navigation.navigateToHome
import org.example.timercenter.ui.AppTopBar
import org.example.timercenter.ui.BottomNavigationBar

@Composable
fun TimerApp() {
    AppTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = { AppTopBar(navController) }, // Верхняя панель
            bottomBar = { BottomNavigationBar(navController) } // Нижняя панель
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation(navController)
            }
        }

    }
}