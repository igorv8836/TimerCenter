package com.example.timercenter.datastore

import android.content.Context
import org.koin.dsl.module

/**
 * Модуль Koin для создания Datatore на Android платформе
 * @return Модуль с созданным Datastore
 */
actual fun createDataStoreModule() = module {
    single { createDataStore(get<Context>()) }
}