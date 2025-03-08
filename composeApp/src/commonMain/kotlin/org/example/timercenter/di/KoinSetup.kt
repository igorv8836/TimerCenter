package org.example.timercenter.di

import com.example.timercenter.common.commonModule
import com.example.timercenter.database.databaseModule
import com.example.timercenter.database.platformDatabaseBuilderModule
import com.example.timercenter.datastore.createDataStoreModule
import com.example.timercenter.datastore.datastoreModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

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
        )

    }

object KoinFactory {
    private var di: Koin? = null

    fun setupKoin(appDeclaration: KoinAppDeclaration = {}) {
        if (di == null) {
            di = initKoin(appDeclaration).koin
        }
    }

    fun getDI(): Koin {
        return di ?: run {
            setupKoin()
            di ?: throw IllegalStateException("Koin is not initialized")
        }
    }
}