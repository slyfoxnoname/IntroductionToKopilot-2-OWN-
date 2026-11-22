package com.example.dice

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.random.Random

class DiceModel {

    suspend fun rollSingleDie(
        iterations: Int,
        onTick: (Int) -> Unit
    ): Int {
        var currentFace = DEFAULT_DIE_VALUE
        repeat(iterations) {
            delay(TICK_DELAY_MS)
            currentFace = Random.nextInt(MIN_DIE_VALUE, MAX_DIE_VALUE + 1)
            onTick(currentFace)
        }
        return currentFace
    }

    suspend fun rollAllDice(
        diceCount: Int = DICE_COUNT,
        onDieUpdated: (index: Int, value: Int) -> Unit
    ): List<Int> = coroutineScope {
        val deferredRolls = List(diceCount) { dieIndex ->
            val iterations = Random.nextInt(MIN_ITERATIONS, MAX_ITERATIONS + 1)
            async {
                rollSingleDie(iterations) { newValue ->
                    onDieUpdated(dieIndex, newValue)
                }
            }
        }
        deferredRolls.awaitAll()
    }

    companion object {
        const val DICE_COUNT = 5
        const val DEFAULT_DIE_VALUE = 6
        private const val MIN_DIE_VALUE = 1
        private const val MAX_DIE_VALUE = 6
        private const val TICK_DELAY_MS = 100L
        private const val MIN_ITERATIONS = 15 // 1.5 сек
        private const val MAX_ITERATIONS = 30 // 3.0 сек
    }
}