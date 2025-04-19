package org.example.timercenter.data

import com.example.timercenter.common.MyDispatchers
import org.example.timercenter.data.repositories.TimerGroupRepositoryImpl
import org.example.timercenter.data.repositories.TimerHistoryRepositoryImpl
import org.example.timercenter.data.repositories.TimerRepositoryImpl
import org.example.timercenter.data.scheduler.timerSchedulerModule
import org.example.timercenter.domain.repositories.TimerGroupRepository
import org.example.timercenter.domain.repositories.TimerHistoryRepository
import org.example.timercenter.domain.repositories.TimerRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Модуль зависимостей для слоя данных.
 * Предоставляет реализации репозиториев и необходимые зависимости.
 * @return Модуль Koin с настройками зависимостей
 */
fun dataModule() = module {
    includes(timerSchedulerModule())
    single<TimerRepository> { TimerRepositoryImpl(get(), get(), get(), get(named(MyDispatchers.IO))) }
    single<TimerGroupRepository> { TimerGroupRepositoryImpl(get(), get(), get(), get(named(MyDispatchers.IO))) }
    single<TimerHistoryRepository> { TimerHistoryRepositoryImpl(get(), get(named(MyDispatchers.IO))) }
}