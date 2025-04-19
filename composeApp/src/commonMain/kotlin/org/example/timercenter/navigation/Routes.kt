package org.example.timercenter.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

/**
 * Маршрут для главного экрана
 * @property timerId Идентификатор таймера (опционально)
 * @property groupId Идентификатор группы (опционально)
 */
@Serializable
data class HomeScreenRoute(
    val timerId: Int?,
    val groupId: Int?,
)

/**
 * Маршрут для экрана создания/редактирования таймера
 * @property id Идентификатор таймера для редактирования (опционально)
 */
@Serializable
data class CreateScreenRoute(
    val id: Int?,
)

/**
 * Маршрут для экрана создания/редактирования группы
 * @property id Идентификатор группы для редактирования (опционально)
 */
@Serializable
data class CreateGroupScreenRoute(
    val id: Int?,
)

/**
 * Маршрут для экрана добавления таймеров в группу
 */
@Serializable
data object AddToGroupScreenRoute

/**
 * Маршрут для экрана истории таймеров
 */
@Serializable
data object HistoryScreenRoute

/**
 * Маршрут для экрана настроек
 */
@Serializable
data object SettingsScreenRoute

/**
 * Навигация на главный экран
 * @param timerId Идентификатор таймера (опционально)
 * @param groupId Идентификатор группы (опционально)
 */
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

/**
 * Навигация на экран создания/редактирования таймера
 * @param id Идентификатор таймера для редактирования (опционально)
 */
fun NavController.navigateToCreate(
    id: Int? = null,
) {
    navigate(CreateScreenRoute(id))
}

/**
 * Навигация на экран создания/редактирования группы
 * @param id Идентификатор группы для редактирования (опционально)
 */
fun NavController.navigateToCreateGroup(
    id: Int? = null,
) {
    navigate(CreateGroupScreenRoute(id))
}

/**
 * Навигация на экран добавления таймеров в группу
 */
fun NavController.navigateToAddToGroup() {
    navigate(AddToGroupScreenRoute)
}

/**
 * Навигация на экран настроек
 */
fun NavController.navigateToSettings() {
    navigate(SettingsScreenRoute)
}

/**
 * Навигация на экран истории таймеров
 */
fun NavController.navigateToHistory() {
    navigate(HistoryScreenRoute)
}
