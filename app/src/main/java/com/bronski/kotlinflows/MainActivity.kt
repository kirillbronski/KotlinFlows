package com.bronski.kotlinflows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bronski.kotlinflows.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        lifecycleScope.launchWhenStarted {
            viewModel.countDownFlow.collectLatest {
                binding.tvCountdownTimer.text = it.toString()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}