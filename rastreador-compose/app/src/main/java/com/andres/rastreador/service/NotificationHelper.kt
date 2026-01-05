
package com.andres.rastreador.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.andres.rastreador.R

const val CHANNEL_ID = "tracking_channel"
const val NOTIF_ID = 1001

object NotificationHelper {
    fun ensureChannel(context: Context) {
        val mgr = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Rastreo de ubicación",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Notificación persistente del servicio de rastreo"
            enableLights(false)
            enableVibration(false)
            lightColor = Color.MAGENTA
        }
        mgr.createNotificationChannel(channel)
    }

    fun buildNotification(context: Context, discreta: Boolean, content: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_location)
            .setContentTitle("Rastreo activo")
            .setContentText(if (discreta) "Rastreando ubicación…" else content)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
