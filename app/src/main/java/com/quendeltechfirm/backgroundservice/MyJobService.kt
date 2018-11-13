package com.quendeltechfirm.backgroundservice

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {
    val TAG = MyJobService::class.java.simpleName
    var isCancled = false

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "Job scheduled")
        performTask(p0)
        return true //true if your service will continue running,
        // using a separate thread when appropriate. false means that this job has completed its work.
    }

    private fun performTask(p0: JobParameters?) {
        Thread {
            for (i in 0..10) {
                if (isCancled) return@Thread
                Log.d(TAG, "$i")
                Thread.sleep(1000)
            }
            Log.d(TAG, "Job finished")
            jobFinished(p0, false)
        }.start()
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "Job canceled")
        isCancled = true
        return true // want to reschedule it set true otherwise false
    }
}