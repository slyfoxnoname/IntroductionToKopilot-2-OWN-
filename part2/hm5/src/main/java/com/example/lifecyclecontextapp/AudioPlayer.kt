package com.example.lifecyclecontextapp

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/**
 * Завдання 03 (Частина 1): Власний компонент життєвого циклу на базі DefaultLifecycleObserver.
 * Автоматично запускає та призупиняє відтворення фонового аудіо при зміні видимості екрана.
 */
class AudioPlayer : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Timber.d("AudioPlayer: Відтворення фонового аудіо розпочато")
    }

    override fun onStop(owner: LifecycleOwner) {
        Timber.d("AudioPlayer: Відтворення фонового аудіо призупинено")
    }
}
