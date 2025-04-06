package org.example.timercenter.services

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.IBinder

class AlarmSoundService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val timerId = intent?.getIntExtra("TIMER_ID", -1) ?: -1

        if (intent?.action == "STOP_ALARM") {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(timerId)
            stopSelf()
            return START_NOT_STICKY
        }

        if (mediaPlayer == null) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            mediaPlayer = MediaPlayer.create(this, alarmSound)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
