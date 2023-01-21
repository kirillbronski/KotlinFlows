package com.bronski.kotlinflows

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    val countDownFlow = flow {
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0){
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }

}