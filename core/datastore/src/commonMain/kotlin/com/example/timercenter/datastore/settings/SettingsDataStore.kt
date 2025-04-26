package com.example.timercenter.datastore.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Управляет настройками приложения с использованием DataStore
 * @property dataStore Экземпляр DataStore для хранения настроек
 */
internal class SettingsDataStore(private val dataStore: DataStore<Preferences>) {
    /**
     * Поток настроек типа уведомлений
     */
    val notificationType: Flow<Int> = dataStore.data.map { prefs ->
        prefs[SettingsKeys.NOTIFICATION_TYPE] ?: DEFAULT_NOTIFICATION_TYPE
    }

    /**
     * Обновляет настройку типа уведомлений
     * @param type Новое значение типа уведомлений
     */
    suspend fun setNotificationType(type: Int) {
        dataStore.edit { prefs ->
            prefs[SettingsKeys.NOTIFICATION_TYPE] = type
        }
    }

    companion object {
        private const val DEFAULT_NOTIFICATION_TYPE = 3 // 3 - уведомления в виде текста
    }
}

/**
 * Содержит ключи для доступа к настройкам в DataStore
 */
object SettingsKeys {
    val NOTIFICATION_TYPE = intPreferencesKey("notification_type")
}