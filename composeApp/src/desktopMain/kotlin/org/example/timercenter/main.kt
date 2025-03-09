package org.example.timercenter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.timercenter.di.KoinFactory

fun main() {
    KoinFactory.setupKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TimerCenter",
        ) {
            MyApp()
        }
    }
}