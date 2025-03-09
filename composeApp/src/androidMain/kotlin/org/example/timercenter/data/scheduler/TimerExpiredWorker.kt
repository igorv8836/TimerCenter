package org.example.timercenter.data.scheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class TimerExpiredWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val timerId = inputData.getInt("TIMER_ID", -1)
        if (timerId == -1) return Result.failure()
        sendTimerFinishedNotification(timerId)
        return Result.success()
    }

    private fun sendTimerFinishedNotification(timerId: Int) {
        val context = applicationContext
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("timer_channel", "Timer Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, "timer_channel")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Таймер завершён")
            .setContentText("Таймер #$timerId завершён.")
            .setAutoCancel(true)
            .build()
        notificationManager.notify(timerId, notification)
    }
}