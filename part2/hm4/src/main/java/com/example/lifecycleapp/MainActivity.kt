package com.example.lifecycleapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("MainActivity успішно запущена!")
        Timber.d("MainActivity: onCreate() викликано")

        val counterText = findViewById<TextView>(R.id.tvCounter)
        val btnIncrement = findViewById<Button>(R.id.btnIncrementCounter)
        val btnOpenSecond = findViewById<Button>(R.id.btnOpenSecondActivity)

        val app = application as MyApp
        counterText.text = "Поточне значення startCount: ${app.startCount}"

        // Обробник для додаткового завдання (⭐)
        btnIncrement.setOnClickListener {
            app.startCount++
            counterText.text = "Поточне значення startCount: ${app.startCount}"
            Timber.d("Кнопку натиснуто! Поточний startCount у MyApp = ${app.startCount}")
        }

        // Перехід на SecondActivity (Частина 3 Завдання 2)
        btnOpenSecond.setOnClickListener {
            Timber.d("MainActivity: натиснуто кнопку переходу на SecondActivity")
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("MainActivity: onStart() викликано")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity: onResume() викликано")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("MainActivity: onPause() викликано")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("MainActivity: onStop() викликано")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MainActivity: onDestroy() викликано")
    }
}
