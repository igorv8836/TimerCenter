package org.example.timercenter

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() =
    ComposeUIViewController { TimerApp(timeAgoManager = remember { TimeAgoManager() } ) }