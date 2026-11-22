package com.example.lifecyclecontextapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Зверніть увагу (Завдання 03, Частина 1 і 2):
 * У MainActivity немає перевизначень onStart()/onStop()/onResume()/onPause()!
 * Уся логіка винесена в AudioPlayer та AnalyticsTracker, які підписані через lifecycle.addObserver(...).
 */
class MainActivity : AppCompatActivity() {

    private val audioPlayer = AudioPlayer()
    private val analyticsTracker = AnalyticsTracker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Завдання 03: Реєстрація спостерігачів життєвого циклу Activity
        lifecycle.addObserver(audioPlayer)
        lifecycle.addObserver(analyticsTracker)

        // Завдання 05: Безпечна ініціалізація синглтона CustomToastManager
        CustomToastManager.init(this)

        // Кнопка для перевірки CustomToastManager (Спосіб А - applicationContext)
        findViewById<Button>(R.id.btnShowToast).setOnClickListener {
            CustomToastManager.showToast("Тест (applicationContext)")
        }

        // Кнопка для перевірки CustomToastManager (Спосіб Б - WeakReference)
        findViewById<Button>(R.id.btnShowWeakToast).setOnClickListener {
            CustomToastManager.showToastViaWeakRef("Тест (WeakReference)")
        }

        // Кнопка для переходу на SecondActivity (перевірка переходів між екранами у Завданнях 03 і 04)
        findViewById<Button>(R.id.btnNavigateSecond).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
