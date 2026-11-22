package com.example.mvvm

import android.os.CountDownTimer
import android.util.Log

class TimerModel {

    private var countDownTimer: CountDownTimer? = null

    fun start(
        durationSeconds: Long = DEFAULT_DURATION_SECONDS,
        onTick: (Long) -> Unit,
        onFinish: () -> Unit
    ) {
        stop()

        val totalMillis = durationSeconds * MILLIS_IN_SECOND
        Log.d(TAG, "Starting timer for $durationSeconds seconds")

        countDownTimer = object : CountDownTimer(totalMillis, TICK_INTERVAL_MS) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished + MILLIS_IN_SECOND - 1) / MILLIS_IN_SECOND
                Log.d(TAG, "Tick: $secondsRemaining sec remaining")
                onTick(secondsRemaining)
            }

            override fun onFinish() {
                Log.d(TAG, "Timer finished")
                countDownTimer = null
                onFinish()
            }
        }.start()
    }

    fun stop() {
        countDownTimer?.let {
            it.cancel()
            Log.d(TAG, "Timer cancelled")
        }
        countDownTimer = null
    }

    companion object {
        const val DEFAULT_DURATION_SECONDS = 30L
        private const val MILLIS_IN_SECOND = 1_000L
        private const val TICK_INTERVAL_MS = 1_000L
        private const val TAG = "TimerModel"
    }
}