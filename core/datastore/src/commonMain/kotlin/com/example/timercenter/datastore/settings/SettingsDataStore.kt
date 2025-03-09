package com.example.timercenter.datastore.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {
    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[SettingsKeys.NOTIFICATIONS_ENABLED] != false
    }

    val lastActiveTimerId: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[SettingsKeys.LAST_ACTIVE_TIMER_ID]
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[SettingsKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setLastActiveTimerId(timerId: Int?) {
        dataStore.edit { prefs ->
            if (timerId == null) {
                prefs.remove(SettingsKeys.LAST_ACTIVE_TIMER_ID)
            } else {
                prefs[SettingsKeys.LAST_ACTIVE_TIMER_ID] = timerId
            }
        }
    }
}


object SettingsKeys {
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val LAST_ACTIVE_TIMER_ID = intPreferencesKey("last_active_timer_id")
}