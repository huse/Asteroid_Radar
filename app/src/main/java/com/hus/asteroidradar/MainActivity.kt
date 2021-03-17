package com.hus.asteroidradar

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hus.asteroidradar.worker.AsteroidWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isWorkScheduled = getSharedPreferences("asteroid.PREFS_KEY", Context.MODE_PRIVATE)
            .getBoolean("asteroid.WORK_SCHEDULE_PREF", false)

        if (!isWorkScheduled) {
            Timber.i("work not Scheduled. Worker started." )

            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val saveRequest =
                PeriodicWorkRequestBuilder<AsteroidWorker>(1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance()
                .enqueue(saveRequest)

            getSharedPreferences("asteroid.PREFS_KEY", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("asteroid.WORK_SCHEDULE_PREF", true)
                .apply()
        }
    }
}
