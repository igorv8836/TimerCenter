package com.example.timercenter.datastore

import com.example.timercenter.datastore.settings.SettingsDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

fun datastoreModule() = module {
    includes(createDataStoreModule())

    single { SettingsDataStore(get()) }
}


expect fun createDataStoreModule(): Module