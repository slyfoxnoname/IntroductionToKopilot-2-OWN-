package com.example.dice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DiceViewModel : ViewModel() {

    private val diceModel = DiceModel()
    private val currentDice = MutableList(DiceModel.DICE_COUNT) { DiceModel.DEFAULT_DIE_VALUE }
    private var rollJob: Job? = null

    private val _diceValues = MutableLiveData<List<Int>>(currentDice.toList())
    val diceValues: LiveData<List<Int>> = _diceValues

    private val _isRolling = MutableLiveData(false)
    val isRolling: LiveData<Boolean> = _isRolling

    private val _buttonText = MutableLiveData(TEXT_ROLL)
    val buttonText: LiveData<String> = _buttonText

    fun rollDice() {
        // Якщо обертання вже триває (кнопка RESTART) — скасовуємо поточну корутину і запускаємо заново
        rollJob?.cancel()

        _isRolling.value = true
        _buttonText.value = TEXT_RESTART

        rollJob = viewModelScope.launch {
            val finalValues = diceModel.rollAllDice { index, value ->
                currentDice[index] = value
                _diceValues.value = currentDice.toList()
            }

            // Виконається лише якщо всі 5 корутин завершилися без скасування (без гонки при RESTART)
            _diceValues.value = finalValues
            _isRolling.value = false
            _buttonText.value = TEXT_ROLL
        }
    }

    companion object {
        private const val TEXT_ROLL = "ROLL"
        private const val TEXT_RESTART = "RESTART"
    }
}