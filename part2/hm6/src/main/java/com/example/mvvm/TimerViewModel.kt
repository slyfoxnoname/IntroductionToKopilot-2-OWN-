package com.example.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val timerModel = TimerModel()

    private val _timerLiveData = MutableLiveData<Long>(DEFAULT_TIME_SECONDS)
    val timerLiveData: LiveData<Long> = _timerLiveData

    private val _isTimerRunning = MutableLiveData<Boolean>(false)
    val isTimerRunning: LiveData<Boolean> = _isTimerRunning

    fun startTimer(userSeconds: Long? = null) {
        val duration = if (userSeconds != null && userSeconds > 0L) {
            userSeconds
        } else {
            DEFAULT_TIME_SECONDS
        }

        _timerLiveData.value = duration
        _isTimerRunning.value = true

        timerModel.start(
            durationSeconds = duration,
            onTick = { secondsRemaining ->
                _timerLiveData.value = secondsRemaining
            },
            onFinish = {
                _isTimerRunning.value = false
                _timerLiveData.value = 0L
            }
        )
    }

    fun resetTimer() {
        timerModel.stop()
        _isTimerRunning.value = false
        _timerLiveData.value = DEFAULT_TIME_SECONDS
    }

    override fun onCleared() {
        super.onCleared()
        timerModel.stop()
        Log.d(TAG, "TimerViewModel cleared: timer stopped")
    }

    companion object {
        private const val DEFAULT_TIME_SECONDS = TimerModel.DEFAULT_DURATION_SECONDS
        private const val TAG = "TimerViewModel"
    }
}