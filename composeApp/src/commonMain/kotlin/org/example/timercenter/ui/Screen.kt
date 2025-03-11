package org.example.timercenter.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.timercenter.navigation.CreateScreenRoute
import org.example.timercenter.navigation.HistoryScreenRoute
import org.example.timercenter.navigation.HomeScreenRoute


// 🔹 Модели экранов
//sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
//    object Home : Screen(HomeScreenRoute.toString(), "Главная", Icons.Default.Home)
//    object Create : Screen(CreateScreenRoute.toString(), "Создать", Icons.Default.Add)
//    object History : Screen(HistoryScreenRoute.toString(), "История", Icons.Default.History)
//}

// 🔹 Экранная модель для BottomNavigationBar
data class Screen(val route: String, val icon: ImageVector, val title: String)