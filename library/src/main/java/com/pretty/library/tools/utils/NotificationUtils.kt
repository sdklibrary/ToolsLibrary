package com.pretty.library.tools.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationUtils {

    @JvmStatic
    @JvmOverloads
    fun Context.pushNotify(
        notifyId: Int,
        smallIcon: Int,
        notifyTitle: String,
        notifyContent: String,
        notifyChannelId: String,
        notifyChannelName: String,
        autoCancel: Boolean = true,
        foreground: Boolean = false,
        block: (NotificationCompat.Builder.() -> Unit)? = null
    ) {
        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notifyChannel(notifyManager, notifyChannelId, notifyChannelName)
        val builder = NotificationCompat.Builder(this, notifyChannelId)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setContentTitle(notifyTitle)
        builder.setContentText(notifyContent)
        builder.setSmallIcon(smallIcon)
        builder.setAutoCancel(autoCancel)
        block?.invoke(builder)
        val notification = builder.build()
        notifyManager.notify(notifyId, notification)
        if (this is Service && foreground) {
            startForeground(notifyId, notification)
        }
    }

    /**
     * 创建通道
     */
    private fun notifyChannel(
        notifyManager: NotificationManager,
        channelId: String,
        channelName: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notifyManager.createNotificationChannel(channel)
        }
    }

    /**
     * 取消通知
     */
    fun cancelNotify(context: Context, notifyId: Int) {
        val notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notifyManager.cancel(notifyId)
    }
}