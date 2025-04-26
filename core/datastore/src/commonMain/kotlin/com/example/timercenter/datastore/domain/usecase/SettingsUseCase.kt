package com.example.timercenter.datastore.domain.usecase

import com.example.timercenter.datastore.models.NotificationType
import com.example.timercenter.datastore.models.toInt
import com.example.timercenter.datastore.models.toNotificationType
import com.example.timercenter.datastore.settings.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsUseCase private constructor(
    private val settingsDataStore: SettingsDataStore
) {
    suspend fun saveNotificationType(type: NotificationType) {
        settingsDataStore.setNotificationType(type.toInt())
    }

    fun getNotificationTypeFlow(): Flow<NotificationType> = settingsDataStore.notificationType
        .map { it.toNotificationType() ?: NotificationType.TEXT }

    suspend fun getNotificationType(): NotificationType = settingsDataStore.notificationType
        .first().toNotificationType() ?: NotificationType.TEXT

    companion object {
        internal fun create(
            settingsDataStore: SettingsDataStore
        ): SettingsUseCase {
            return SettingsUseCase(
                settingsDataStore = settingsDataStore,
            )
        }
    }
}