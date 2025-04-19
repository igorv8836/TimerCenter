package com.example.timercenter.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Модуль Koin для общих зависимостей
 * @return Модуль с зарегистрированными диспетчерами и скоупами
 */
fun commonModule() = module {
    single(named(MyDispatchers.IO)) { provideIoDispatcher() }

    single(named(MyDispatchers.Default)) { provideDefaultDispatcher() }
    single { provideDefaultDispatcher() }

    single {
        provideApplicationScope(
            get(named(MyDispatchers.Default))
        )
    }
}

/**
 * Предоставляет IO диспетчер
 * @return IO диспетчер
 */
internal fun provideIoDispatcher() = Dispatchers.IO

/**
 * Предоставляет Default диспетчер
 * @return Default диспетчер
 */
internal fun provideDefaultDispatcher() = Dispatchers.Default

/**
 * Предоставляет корневой скоуп приложения
 * @param dispatcher Диспетчер для скоупа
 * @return Корневой скоуп приложения
 */
internal fun provideApplicationScope(dispatcher: CoroutineDispatcher): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcher)

/**
 * Перечисление доступных диспетчеров
 */
enum class MyDispatchers {
    IO, Default
}