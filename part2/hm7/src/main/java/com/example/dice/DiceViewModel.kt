package com.example.dice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    private val stateLock = Any()
    private val currentDice = IntArray(DICE_COUNT) { DEFAULT_DIE_VALUE }
    private val activeThreads = CopyOnWriteArrayList<Thread>()

    @Volatile
    private var isCleared = false

    private val _diceValues = MutableLiveData(currentDice.toList())
    val diceValues: LiveData<List<Int>> = _diceValues

    private val _isRolling = MutableLiveData(false)
    val isRolling: LiveData<Boolean> = _isRolling

    fun rollDice() {
        if (_isRolling.value == true) return

        _isRolling.value = true
        activeThreads.clear()

        val remainingThreads = AtomicInteger(DICE_COUNT)

        for (dieIndex in 0 until DICE_COUNT) {
            val iterations = Random.nextInt(MIN_ITERATIONS, MAX_ITERATIONS + 1)

            val worker = thread(name = "DiceRoller-${dieIndex + 1}") {
                try {
                    repeat(iterations) {
                        if (isCleared || Thread.currentThread().isInterrupted) return@thread

                        val rolledValue = Random.nextInt(MIN_DIE_VALUE, MAX_DIE_VALUE + 1)
                        val snapshot = synchronized(stateLock) {
                            currentDice[dieIndex] = rolledValue
                            currentDice.toList()
                        }
                        _diceValues.postValue(snapshot)

                        Thread.sleep(TICK_DELAY_MS)
                    }
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                } finally {
                    if (remainingThreads.decrementAndGet() == 0 && !isCleared) {
                        _isRolling.postValue(false)
                    }
                }
            }

            activeThreads.add(worker)
        }
    }

    override fun onCleared() {
        super.onCleared()
        isCleared = true
        activeThreads.forEach { it.interrupt() }
        activeThreads.clear()
    }

    companion object {
        private const val DICE_COUNT = 5
        private const val DEFAULT_DIE_VALUE = 6
        private const val MIN_DIE_VALUE = 1
        private const val MAX_DIE_VALUE = 6
        private const val TICK_DELAY_MS = 100L
        private const val MIN_ITERATIONS = 15 // 1.5 сек
        private const val MAX_ITERATIONS = 30 // 3.0 сек
    }
}