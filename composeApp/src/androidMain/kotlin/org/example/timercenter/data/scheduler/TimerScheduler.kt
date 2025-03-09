package org.example.timercenter.data.scheduler

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

actual class TimerScheduler(private val context: Context) {
    actual suspend fun scheduleTimer(timerId: Int, delayMillis: Long) {
        val workManager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequestBuilder<TimerExpiredWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("TIMER_ID" to timerId))
            .build()
        workManager.enqueueUniqueWork("timer_finished_$timerId", ExistingWorkPolicy.REPLACE, workRequest)
    }

    actual suspend fun cancelTimer(timerId: Int) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork("timer_finished_$timerId")
    }
}