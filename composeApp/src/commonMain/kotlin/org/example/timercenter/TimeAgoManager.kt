package org.example.timercenter

import org.koin.core.module.Module

expect class TimeAgoManager {
    fun timeAgo(lastStartedTime: Long): String
    fun currentTimeMillis(): Long
}

expect fun timeManagerModule(): Module