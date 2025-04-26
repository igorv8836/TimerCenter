package com.example.timercenter.datastore

import com.example.timercenter.datastore.domain.usecase.SettingsUseCase
import com.example.timercenter.datastore.settings.SettingsDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Создает модуль Koin для зависимостей DataStore
 * @return Настроенный модуль Koin
 */
fun datastoreModule() = module {
    includes(createDataStoreModule())

    single { SettingsDataStore(get()) }
    single<SettingsUseCase> { SettingsUseCase.create(get()) }
}

/**
 * Создает платформо-специфичный модуль DataStore
 * @return Платформо-специфичный модуль Koin
 */
expect fun createDataStoreModule(): Module