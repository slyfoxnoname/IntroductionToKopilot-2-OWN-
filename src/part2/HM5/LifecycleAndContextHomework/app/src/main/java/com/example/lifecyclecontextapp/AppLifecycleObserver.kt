package com.example.lifecyclecontextapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * Завдання 04 (Частини 2 та 3): Спостерігач за життєвим циклом усього процесу через ProcessLifecycleOwner.
 * Включає таймер перебування у фоновому режимі (> 5 секунд).
 */
class AppLifecycleObserver(
    private val appContext: Context
) : DefaultLifecycleObserver {

    private var backgroundTimestamp: Long = 0L

    override fun onStart(owner: LifecycleOwner) {
        Timber.d("ProcessLifecycle: Застосунок у Foreground (onStart)")

        if (backgroundTimestamp != 0L && (System.currentTimeMillis() - backgroundTimestamp) > 5_000L) {
            val message = "Ви були відсутні більше 5 секунд! Сесію оновлено."
            Timber.d("ProcessLifecycle: $message")
            Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        backgroundTimestamp = System.currentTimeMillis()
        Timber.d("ProcessLifecycle: Застосунок у Background (onStop)")
    }
}
