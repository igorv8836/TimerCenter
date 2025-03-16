package com.example.timercenter.datastore.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {
    val notificationType: Flow<Int> = dataStore.data.map { prefs ->
        prefs[SettingsKeys.NOTIFICATION_TYPE] ?: DEFAULT_NOTIFICATION_TYPE
    }

    suspend fun setNotificationType(type: Int) {
        dataStore.edit { prefs ->
            prefs[SettingsKeys.NOTIFICATION_TYPE] = type
        }
    }

    companion object {
        private const val DEFAULT_NOTIFICATION_TYPE = 3 // 3 - уведомления в виде текста
    }
}


object SettingsKeys {
    val NOTIFICATION_TYPE = intPreferencesKey("notification_type")

}