package com.example.timercenter.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

/**
 * Создает экземпляр DataStore с указанным путем
 * @param producePath Функция, предоставляющая путь к файлу DataStore
 * @return Настроенный экземпляр DataStore
 */
fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

/**
 * Имя файла настроек DataStore
 */
internal const val dataStoreFileName = "prefs.preferences_pb"