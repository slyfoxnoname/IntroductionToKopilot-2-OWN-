package com.example.lifecyclecontextapp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * Завдання 03 (Частина 2): Спостерігач життєвого циклу на базі LifecycleEventObserver.
 * Відстежує фокус взаємодії користувача з екраном через події ON_RESUME та ON_PAUSE.
 */
class AnalyticsTracker : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                Timber.d("AnalyticsTracker: Користувач взаємодіє з екраном (ON_RESUME)")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Timber.d("AnalyticsTracker: Екран втратив фокус (ON_PAUSE)")
            }
            else -> {}
        }
    }
}
