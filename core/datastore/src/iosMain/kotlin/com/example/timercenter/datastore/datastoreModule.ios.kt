package com.example.timercenter.datastore

import org.koin.dsl.module

actual fun createDataStoreModule() = module {
    single { createDataStore() }
}