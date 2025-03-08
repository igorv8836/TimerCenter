package org.example.timercenter.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenRoute

@Serializable
data object StartScreenRoute


fun NavController.navigateToMain() {
    navigate(MainScreenRoute)
}

fun NavController.navigateToStart() {
    navigate(StartScreenRoute)
}