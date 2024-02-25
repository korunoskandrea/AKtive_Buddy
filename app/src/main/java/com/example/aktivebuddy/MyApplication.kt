package com.example.aktivebuddy
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.aktivebuddy.database.model.Fitness
import com.example.aktivebuddy.notifications.NotificationHelper
import java.util.UUID
class MyApplication : Application() {
    val fitnessList = mutableListOf<Fitness>();

    fun addFitness(fitness: Fitness) {
        fitnessList.add(fitness)
    }

    fun setFitness(position: Int, fitness: Fitness) {
        fitnessList[position] = fitness;
    }


    fun deleteFitnessAtPosition(position: Int) {
        fitnessList.removeAt(position);
    }

    override fun onCreate() {
        super.onCreate()

        val notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel()

        val sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        // Create UUID
        val uuid = sharedPreferences.getString("UUID", null)
        if (uuid == null) {
            sharedPreferences.edit().putString("UUID", UUID.randomUUID().toString()).apply()
        }

        var openCount = sharedPreferences.getInt("openCount", 0)
        openCount++;
        sharedPreferences.edit().putInt("openCount", openCount).apply()
        registerActivityLifecycleCallbacks(AppLifecycleCallbacks())
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        val sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        var backgroundCount = sharedPreferences.getInt("backgroundCount", 0)
        backgroundCount++;
        sharedPreferences.edit().putInt("backgroundCount", backgroundCount).apply()
    }

    private inner class AppLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        private var sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {
            var activityOpened = sharedPreferences.getInt(activity.localClassName, 0)
            activityOpened++;
            sharedPreferences.edit().putInt(activity.localClassName, activityOpened).apply()
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

    }

}