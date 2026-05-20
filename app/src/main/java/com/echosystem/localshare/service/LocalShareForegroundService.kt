package com.echosystem.localshare.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.echosystem.localshare.R

class LocalShareForegroundService : Service() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LocalShareForegroundService::class.java)
            context.startForegroundService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel("localshare_channel", "LocalShare", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        
        val notification = NotificationCompat.Builder(this, "localshare_channel")
            .setContentTitle("LocalShare Running")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
            
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
