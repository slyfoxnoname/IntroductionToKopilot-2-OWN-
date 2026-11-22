package com.example.mvvm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
        setupListeners()
    }

    private fun observeViewModel() {
        viewModel.timerLiveData.observe(this) { seconds ->
            binding.tvTimer.text = if (seconds == 0L) {
                "Час вийшов!"
            } else {
                "Залишилось: $seconds сек"
            }
        }

        viewModel.isTimerRunning.observe(this) { isRunning ->
            binding.btnStart.isEnabled = !isRunning
            binding.etSeconds.isEnabled = !isRunning
        }
    }

    private fun setupListeners() {
        binding.btnStart.setOnClickListener {
            val userSeconds = binding.etSeconds.text?.toString()?.trim()?.toLongOrNull()
            viewModel.startTimer(userSeconds)
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetTimer()
        }
    }
}