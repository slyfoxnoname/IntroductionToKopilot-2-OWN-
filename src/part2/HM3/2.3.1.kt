package HM3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SimpleCalculatorActivity : AppCompatActivity() {

    private val TAG = "SimpleCalculator"

    private lateinit var etFirstNumber: EditText
    private lateinit var etSecondNumber: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_calculator)

        // Ініціалізація за допомогою класичного findViewById
        etFirstNumber = findViewById(R.id.etFirstNumber)
        etSecondNumber = findViewById(R.id.etSecondNumber)
        tvResult = findViewById(R.id.tvResult)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
        btnClear = findViewById(R.id.btnClear)

        btnAdd.setOnClickListener { calculate("+") }
        btnSubtract.setOnClickListener { calculate("-") }
        btnMultiply.setOnClickListener { calculate("*") }
        btnDivide.setOnClickListener { calculate("/") }

        // Додаткове завдання: кнопка очищення
        btnClear.setOnClickListener {
            etFirstNumber.text.clear()
            etSecondNumber.text.clear()
            tvResult.text = "Результат: "
            Log.d(TAG, "Всі поля очищено")
        }
    }

    private fun calculate(operation: String) {
        val firstInput = etFirstNumber.text.toString().trim()
        val secondInput = etSecondNumber.text.toString().trim()

        // Перевірка на порожні поля
        if (firstInput.isEmpty() || secondInput.isEmpty()) {
            val errorMsg = "Будь ласка, введіть обидва числа"
            tvResult.text = errorMsg
            Log.w(TAG, "Помилка валідації: одне або обидва поля порожні")
            return
        }

        val num1 = firstInput.toDoubleOrNull()
        val num2 = secondInput.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            val errorMsg = "Некоректний формат числа"
            tvResult.text = errorMsg
            Log.w(TAG, "Помилка парсингу: не вдалося розпізнати числа ($firstInput, $secondInput)")
            return
        }

        Log.d(TAG, "Виконання операції: $num1 $operation $num2")

        // Обробка ділення на нуль
        if (operation == "/" && num2 == 0.0) {
            val zeroDivisionMsg = "Помилка: ділення на нуль неможливе!"
            tvResult.text = zeroDivisionMsg
            Log.w(TAG, "Спроба ділення на нуль: $num1 / 0")
            return
        }

        val result = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> 0.0
        }

        tvResult.text = "Результат: ${formatResult(result)}"
        Log.d(TAG, "Результат обчислення: $result")
    }

    // Форматування результату без зайвих нулів (наприклад, 5.0 -> 5, 5.5 -> 5.5)
    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}