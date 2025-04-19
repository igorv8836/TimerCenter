package org.example.timercenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember

/**
 * Главная точка входа для Android-приложения TimerCenter
 * Данная активность инициализирует Compose UI и управляет жизненным циклом приложения
 */
class MainActivity : ComponentActivity() {
    /**
     * Инициализирует активность и настраивает Compose UI
     * @param savedInstanceState Bundle, содержащий предыдущее сохраненное состояние активности
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotificationPermissionRequester()
            TimerApp(timeAgoManager = remember {
                TimeAgoManager()
            })
        }
    }
}