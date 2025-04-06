package org.example.timercenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
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