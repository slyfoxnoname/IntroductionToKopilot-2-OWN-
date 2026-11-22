package com.example.lifecycleapp

import android.app.Application
import timber.log.Timber

class MyApp : Application() {

    // Додаткове завдання (⭐): глобальний лічильник натискань у межах життєвого циклу процесу
    var startCount: Int = 0

    override fun onCreate() {
        super.onCreate()

        // Ініціалізуємо Timber лише для Debug-збірки
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.d("MyApp: onCreate() викликано — процес застосунку ініціалізовано")
    }
}
