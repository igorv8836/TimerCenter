package org.example.timercenter.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

@Serializable
data object CreateScreenRoute

@Serializable
data object HistoryScreenRoute


fun NavController.navigateToHome() {
    navigate(HomeScreenRoute)
}

fun NavController.navigateToCreate() {
    navigate(CreateScreenRoute)
}

fun NavController.navigateToHistory() {
    navigate(HistoryScreenRoute)
}
