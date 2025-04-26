package org.example.timercenter

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.example.timercenter.di.KoinFactory

fun main() {
    KoinFactory.setupKoin()
    application {
        val state = rememberWindowState()
        state.size = DpSize(
            width = Dp(400f),
            height = Dp(800f),
        )
        Window(
            onCloseRequest = ::exitApplication,
            title = "TimerCenter",
            state = state,
            alwaysOnTop = true,
        ) {
            TimerApp(timeAgoManager = remember { TimeAgoManager() })
        }
    }
}