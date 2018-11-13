package com.quendeltechfirm.backgroundservice

import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startForegroundService(v: View) {
        val intent = Intent(this, MyBackGroundService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

    fun stopForegroundService(v: View) {
        val intent = Intent(this, MyBackGroundService::class.java)
        stopService(intent)
    }

    fun startJobService(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val component = ComponentName(this, MyJobService::class.java)
            val info = JobInfo.Builder(121, component)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build()
            val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val result = scheduler.schedule(info)
            if (result == JobScheduler.RESULT_SUCCESS) {
                Log.d("MainActivity", "JOB SCHEDULED")
            } else {
                Log.e("MainActivity", "JOB SCHEDULE failed")
            }
        } else {
            Log.d("MainActivity", "VERSION.SDK_INT < LOLLIPOP")
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stopJobService(v: View) {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(121)
    }
}
