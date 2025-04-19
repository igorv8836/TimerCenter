package org.example.timercenter.di

import com.example.timercenter.common.commonModule
import com.example.timercenter.database.databaseModule
import com.example.timercenter.database.platformDatabaseBuilderModule
import com.example.timercenter.datastore.createDataStoreModule
import com.example.timercenter.datastore.datastoreModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.example.timercenter.data.dataModule
import org.example.timercenter.timeManagerModule
import org.example.timercenter.ui.uiModule
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Основной класс для управления Koin-ом
 * Инициализирует Koin с необходимыми модулями
 * @param appDeclaration Дополнительные настройки Koin
 * @return Экземпляр Koin
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        Napier.base(DebugAntilog())
        appDeclaration()
        modules(
            commonModule(),
            platformDatabaseBuilderModule(),
            databaseModule(),
            createDataStoreModule(),
            datastoreModule(),
            dataModule(),
            uiModule(),
            timeManagerModule(),
        )
    }

/**
 * Фабрика для работы с Koin
 * Обеспечивает единую точку доступа к зависимостям
 */
object KoinFactory {
    private var di: Koin? = null

    /**
     * Настраивает Koin с дополнительными параметрами
     * @param appDeclaration Дополнительные настройки Koin
     */
    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration).koin
        }
    }

    /**
     * Получает экземпляр Koin
     * @return Экземпляр Koin
     * @throws IllegalStateException если Koin не инициализирован
     */
    fun getDI(): Koin {
        return di ?: run {
            setupKoin()
            di ?: throw IllegalStateException("Koin is not initialized")
        }
    }
}