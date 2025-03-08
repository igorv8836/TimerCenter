package com.example.timercenter.datastore

import org.koin.core.module.Module
import org.koin.dsl.module

fun datastoreModule() = module {
    includes(createDataStoreModule())

    single { TestingStore(get()) }
}


expect fun createDataStoreModule(): Module