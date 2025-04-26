package org.example.timercenter.data.scheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.timercenter.datastore.domain.usecase.SettingsUseCase
import com.example.timercenter.datastore.models.NotificationType
import org.example.timercenter.data.models.WorkManagerConstants
import org.example.timercenter.di.KoinFactory
import org.example.timercenter.services.AlarmSoundService

/**
 * Worker для обработки завершения таймера
 * Выполняет следующие действия при завершении таймера:
 * - Включает вибрацию
 * - Запускает звуковое оповещение
 * - Показывает уведомление с возможностью отключения звука
 */
class TimerExpiredWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    private val settingsUseCase: SettingsUseCase = KoinFactory.getDI().get()
    private val notificationChannelId = "timer"
    
    /**
     * Основной метод выполнения работы
     * @return Результат выполнения работы
     */
    override suspend fun doWork(): Result {
        val notificationType = settingsUseCase.getNotificationType()
        val timerId = inputData.getInt(WorkManagerConstants.TIMER_ID, -1)
        val timerName = inputData.getString(WorkManagerConstants.TIMER_NAME)
            ?: WorkManagerConstants.UNKNOWN_TIMER_NAME

        if (notificationType != NotificationType.TEXT) {
            val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(1000)
            }
        }
        if (notificationType == NotificationType.SOUND) {
            val alarmIntent = Intent(applicationContext, AlarmSoundService::class.java)
            applicationContext.startService(alarmIntent)
        }

        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Timer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        val stopIntent = Intent(applicationContext, AlarmSoundService::class.java).apply {
            action = "STOP_ALARM"
            putExtra("TIMER_ID", timerId)
        }

        val pendingStopIntent = PendingIntent.getService(
            applicationContext,
            timerId,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(applicationContext, notificationChannelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Таймер завершён")
            .setContentText("Таймер $timerName завершил отсчет")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(android.R.drawable.ic_media_pause, "Отключить таймер", pendingStopIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(timerId, notification)
        return Result.success()
    }
}
