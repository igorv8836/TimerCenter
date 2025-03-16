package org.example.timercenter

import kotlinx.datetime.Clock
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class TimeAgoManager {
    actual fun timeAgo(lastStartedTime: Long): String {
        val currentTime = (NSDate().timeIntervalSince1970 * 1000).toLong()
        val elapsedTime = currentTime - lastStartedTime

        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days > 6 -> "a week ago"
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            seconds > 0 -> "$seconds second${if (seconds > 1) "s" else ""} ago"
            else -> "just now"
        }
    }
    actual fun currentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
}