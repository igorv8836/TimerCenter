package org.example.timercenter.data.scheduler

actual class TimerScheduler {
    actual suspend fun scheduleTimer(
        timerId: Int,
        timerName: String,
        delayMillis: Long,
    ) {

    }

    actual suspend fun cancelTimer(timerId: Int) {

    }
}