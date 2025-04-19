package com.example.timercenter.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Функция для создания экземпляра DataStore для платформы Android
 * @param context Контекст Android приложения
 * @return Настроенный экземпляр DataStore
 */
fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)