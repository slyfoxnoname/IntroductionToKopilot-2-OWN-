package com.example.coroutineserrors

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coroutineserrors.databinding.ActivityMainBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var calculationJob: Job? = null

    // Блок 4: Глобальний обробник необроблених помилок корутин
    private val ceh = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Глобальне перехоплення крашу: ${throwable.message}")
        appendUiLog("CEH: Глобальне перехоплення крашу: ${throwable.message}")
    }

    private val safeScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + ceh)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBlock1()
        setupBlock2()
        setupBlock3()
        setupBlock4()
        setupBonusTrap()
    }

    // Блок 1: Кооперативне скасування важкого циклу
    private fun setupBlock1() {
        binding.btnStart.setOnClickListener {
            calculationJob?.cancel()
            appendUiLog("--- Блок 1: Старт обчислень ---")

            calculationJob = lifecycleScope.launch(Dispatchers.Default) {
                try {
                    for (i in 1..100) {
                        Thread.sleep(50)
                        // Для перевірки кроку 3 (без кооперації) можна тимчасово закоментувати ensureActive()
                        ensureActive()
                        Log.d(TAG, "Обробка: $i%")
                        if (i % 10 == 0) {
                            appendUiLog("Обробка: $i%")
                        }
                    }
                    Log.i(TAG, "Роботу успішно завершено!")
                    appendUiLog("Роботу успішно завершено!")
                } catch (e: CancellationException) {
                    Log.w(TAG, "Обробку скасовано користувачем!")
                    appendUiLog("Обробку скасовано користувачем!")
                    throw e
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            calculationJob?.cancel()
        }
    }

    // Блок 2: Ланцюгова реакція при помилках (звичайний Job -> краш і скасування "сестри")
    private fun setupBlock2() {
        binding.btnTestCrash.setOnClickListener {
            appendUiLog("--- Блок 2: Тест ланцюгового крашу ---")

            lifecycleScope.launch {
                launch {
                    delay(1000)
                    throw RuntimeException("Банер не завантажився!")
                }

                launch {
                    delay(2000)
                    Log.i(TAG, "Стрічка новин завантажена")
                    appendUiLog("Стрічка новин завантажена")
                }
            }
        }
    }

    // Блок 3: Ізоляція помилок через supervisorScope + try-catch
    private fun setupBlock3() {
        binding.btnSupervisorScope.setOnClickListener {
            appendUiLog("--- Блок 3: supervisorScope ---")

            lifecycleScope.launch {
                supervisorScope {
                    launch {
                        try {
                            delay(1000)
                            throw RuntimeException("Банер не завантажився!")
                        } catch (e: CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            Log.e(TAG, "Помилка завантаження банера: ${e.message}")
                            appendUiLog("ERROR: Банер не завантажився (${e.message})")
                        }
                    }

                    launch {
                        delay(2000)
                        Log.i(TAG, "Стрічка новин завантажена")
                        appendUiLog("OK: Стрічка новин завантажена")
                    }
                }
            }
        }
    }

    // Блок 4: Кастомний Scope із SupervisorJob + CoroutineExceptionHandler без try-catch
    private fun setupBlock4() {
        binding.btnGlobalCrash.setOnClickListener {
            appendUiLog("--- Блок 4: Global CEH ---")

            safeScope.launch {
                delay(500)
                throw IllegalArgumentException("Ой!")
            }
        }
    }

    // Додаткове завдання ⭐: Пастка вкладеності
    private fun setupBonusTrap() {
        binding.btnNestedTrap.setOnClickListener {
            appendUiLog("--- ⭐ Пастка вкладеності ---")

            lifecycleScope.launch {
                launch(ceh) {
                    throw RuntimeException("Вкладений краш!")
                }
            }
        }
    }

    private fun appendUiLog(message: String) {
        runOnUiThread {
            binding.tvLogs.append("\n$message")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        safeScope.cancel()
    }

    companion object {
        private const val TAG = "CoroutinesLab"
    }
}