package org.example.timercenter.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class HomeScreenRoute(
    val timerId: Int?,
    val groupId: Int?,
)

@Serializable
data class CreateScreenRoute(
    val id: Int?,
)

@Serializable
data class CreateGroupScreenRoute(
    val id: Int?,
)

@Serializable
data object AddToGroupScreenRoute

@Serializable
data object HistoryScreenRoute

@Serializable
data object SettingsScreenRoute


fun NavController.navigateToHome(
    timerId: Int? = null,
    groupId: Int? = null,
) {
    navigate(
        HomeScreenRoute(
            timerId = timerId,
            groupId = groupId,
        )
    )
}

fun NavController.navigateToCreate(
    id: Int? = null,
) {
    navigate(CreateScreenRoute(id))
}

fun NavController.navigateToCreateGroup(
    id: Int? = null,
) {
    navigate(CreateGroupScreenRoute(id))
}

fun NavController.navigateToAddToGroup() {
    navigate(AddToGroupScreenRoute)
}

fun NavController.navigateToSettings() {
    navigate(SettingsScreenRoute)
}

fun NavController.navigateToHistory() {
    navigate(HistoryScreenRoute)
}
