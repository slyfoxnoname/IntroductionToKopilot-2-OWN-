package HM3

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import HM3.databinding.ActivityFullCalculatorBinding

class FullCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullCalculatorBinding

    // Змінні стану
    private var firstOperand: Double? = null
    private var currentOperation: String? = null
    private var isStartingNewInput: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNumberButtons()
        setupOperationButtons()
        setupFunctionalButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        for (btn in numberButtons) {
            btn.setOnClickListener { appendDigit((it as Button).text.toString()) }
        }

        binding.btnDot.setOnClickListener { appendDot() }
    }

    private fun setupOperationButtons() {
        binding.btnAdd.setOnClickListener { setOperation("+") }
        binding.btnSubtract.setOnClickListener { setOperation("-") }
        binding.btnMultiply.setOnClickListener { setOperation("*") }
        binding.btnDivide.setOnClickListener { setOperation("/") }
        binding.btnEquals.setOnClickListener { calculateResult() }
    }

    private fun setupFunctionalButtons() {
        // Очищення (C)
        binding.btnClear.setOnClickListener {
            firstOperand = null
            currentOperation = null
            isStartingNewInput = true
            binding.tvDisplay.text = "0"
        }

        // Видалення останнього символу (Backspace)
        binding.btnBackspace.setOnClickListener {
            val text = binding.tvDisplay.text.toString()
            if (text != "Помилка" && text.isNotEmpty()) {
                val updated = text.dropLast(1)
                binding.tvDisplay.text = if (updated.isEmpty() || updated == "-") "0" else updated
            }
        }

        // Відсоток (%)
        binding.btnPercent.setOnClickListener {
            val current = binding.tvDisplay.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val result = current / 100.0
            binding.tvDisplay.text = formatValue(result)
            isStartingNewInput = true
        }
    }

    private fun appendDigit(digit: String) {
        val currentText = binding.tvDisplay.text.toString()
        if (currentText == "Помилка" || isStartingNewInput || currentText == "0") {
            binding.tvDisplay.text = digit
            isStartingNewInput = false
        } else {
            binding.tvDisplay.append(digit)
        }
    }

    private fun appendDot() {
        val currentText = binding.tvDisplay.text.toString()
        if (isStartingNewInput || currentText == "Помилка") {
            binding.tvDisplay.text = "0."
            isStartingNewInput = false
        } else if (!currentText.contains(".")) {
            binding.tvDisplay.append(".")
        }
    }

    private fun setOperation(op: String) {
        val currentNumber = binding.tvDisplay.text.toString().toDoubleOrNull()
        if (currentNumber == null) return

        // Якщо попередня операція ще не виконана через '=', рахуємо проміжний результат
        if (firstOperand != null && currentOperation != null && !isStartingNewInput) {
            calculateResult()
            firstOperand = binding.tvDisplay.text.toString().toDoubleOrNull()
        } else {
            firstOperand = currentNumber
        }

        currentOperation = op
        isStartingNewInput = true
    }

    private fun calculateResult() {
        val operation = currentOperation ?: return
        val num1 = firstOperand ?: return
        val num2 = binding.tvDisplay.text.toString().toDoubleOrNull()

        if (num2 == null) {
            showError()
            return
        }

        // Перевірка ділення на нуль
        if (operation == "/" && num2 == 0.0) {
            showError()
            return
        }

        val result = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> num2
        }

        binding.tvDisplay.text = formatValue(result)
        firstOperand = null
        currentOperation = null
        isStartingNewInput = true
    }

    private fun showError() {
        binding.tvDisplay.text = "Помилка"
        firstOperand = null
        currentOperation = null
        isStartingNewInput = true
    }

    private fun formatValue(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}