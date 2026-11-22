package com.example.lifecyclecontextapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import timber.log.Timber

class MyApp : Application() {

    // Завдання 04 (Частина 1): Ручний лічильник відкритих (видимих) Activity
    private var startedActivityCount = 0

    override fun onCreate() {
        super.onCreate()

        // Ініціалізуємо Timber для Debug-збірки
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Завдання 04 (Частина 2): Підписуємо AppLifecycleObserver на ProcessLifecycleOwner
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            AppLifecycleObserver(applicationContext)
        )

        // Завдання 03 (Частина 3) + Завдання 04 (Частина 1):
        // Глобальне відстеження станів усіх Activity та ручний детектор Foreground/Background
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            private fun logLifecycleState(eventName: String, activity: Activity) {
                val state = (activity as? LifecycleOwner)?.lifecycle?.currentState
                Timber.d(
                    "ActivityCallbacks -> %s | Екран: %s | Поточний стан: %s",
                    eventName,
                    activity.javaClass.simpleName,
                    state
                )
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                logLifecycleState("onActivityCreated", activity)
            }

            override fun onActivityStarted(activity: Activity) {
                logLifecycleState("onActivityStarted", activity)

                // Ручний підрахунок активних екранів (Завдання 04, Частина 1)
                startedActivityCount++
                if (startedActivityCount == 1) {
                    Timber.d("App status: Застосунок вийшов з фону (Foreground)")
                }
            }

            override fun onActivityResumed(activity: Activity) {
                logLifecycleState("onActivityResumed", activity)
            }

            override fun onActivityPaused(activity: Activity) {
                logLifecycleState("onActivityPaused", activity)
            }

            override fun onActivityStopped(activity: Activity) {
                logLifecycleState("onActivityStopped", activity)

                // Ручний підрахунок активних екранів (Завдання 04, Частина 1)
                startedActivityCount--
                if (startedActivityCount == 0) {
                    Timber.d("App status: Застосунок перейшов у фоновий режим (Background)")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                logLifecycleState("onActivitySaveInstanceState", activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                logLifecycleState("onActivityDestroyed", activity)
            }
        })
    }
}
