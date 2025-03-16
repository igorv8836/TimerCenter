package org.example.timercenter

expect class TimeAgoManager {
    fun timeAgo(lastStartedTime: Long): String
    fun currentTimeMillis(): Long
}