package com.example.timercenter.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class TestingStore(
    private val prefs: DataStore<Preferences>
) {
    private val tokenKey = stringPreferencesKey("testing")

    suspend fun save(token: String){
        prefs.edit {
            it[tokenKey] = token
        }
    }

    fun get() = prefs.data.map {
        it[tokenKey]
    }

    suspend fun remove() {
        prefs.edit {
            it.remove(tokenKey)
        }
    }
}