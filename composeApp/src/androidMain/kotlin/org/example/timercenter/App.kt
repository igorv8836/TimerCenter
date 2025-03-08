package org.example.timercenter

import android.app.Application
import org.example.timercenter.di.KoinFactory
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinFactory.setupKoin {
            androidContext(this@App)
        }
    }
}