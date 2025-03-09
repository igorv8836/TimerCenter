package org.example.timercenter.data.scheduler

expect class TimerScheduler {
    suspend fun scheduleTimer(timerId: Int, delayMillis: Long)
    suspend fun cancelTimer(timerId: Int)
}