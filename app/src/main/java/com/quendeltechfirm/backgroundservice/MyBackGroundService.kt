package com.quendeltechfirm.backgroundservice

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.quendeltechfirm.backgroundservice.App.Companion.CHANNEL_ID

class MyBackGroundService : Service() {

    private val TAG = MyBackGroundService::class.java.simpleName

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand called")

        showNotification()

        return START_NOT_STICKY
    }

    private fun showNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, CHANNEL_ID)
            } else {
                Notification.Builder(this)
            }

        notification.setContentTitle(getString(R.string.app_name))
            .setContentText("This is foreground service")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)

        startForeground(123,notification.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "service destroyed")
    }
}