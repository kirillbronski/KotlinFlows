package com.bronski.kotlinflows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bronski.kotlinflows.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        binding.button.setOnClickListener {
            viewModel.incrementCounter()
        }

        collectLatestLifeCycleFlow(viewModel.stateFlow){
            binding.button.text = it.toString()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

fun <T> ComponentActivity.collectLatestLifeCycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                flow.collectLatest(collect)
            }
        }
}

fun <T> ComponentActivity.collectLifeCycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collect(collect)
        }
    }
}