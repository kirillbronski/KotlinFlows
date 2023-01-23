package com.bronski.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow {
        val startingValue = 5
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow6()
    }

    private fun collectFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            countDownFlow
                .filter {
                    it % 2 == 0
                }
                .map {
                    "map ${it * it}"
                }
                .onEach {
                    println(it)
                }
                .collect {
                    println("The current time is $it")
                }
        }
    }

    private fun collectFlow2() {
        countDownFlow.onEach {
            println("The current time is $it")
        }.launchIn(viewModelScope)
    }

    private fun collectFlow3() {
        viewModelScope.launch(Dispatchers.IO) {
           val count = countDownFlow
                .filter {
                    it % 2 == 0
                }
                .map {
                    it * it
                }
                .onEach {
                    println(it)
                }
                .count {
                    it == 16
                }
            println("The count is $count")
        }
    }

    private fun collectFlow4() {
        viewModelScope.launch(Dispatchers.IO) {
            val reduceResult = countDownFlow
                .reduce { accumulator, value ->
                    println("The accumulator is $accumulator")
                    println("The value is $value")
                    accumulator + value
                }
            println("The reduce is $reduceResult")
        }
    }

    private fun collectFlow5() {
        viewModelScope.launch(Dispatchers.IO) {
            val reduceResult = countDownFlow
                .fold(100) { accumulator, value ->
                    println("The accumulator is $accumulator")
                    println("The value is $value")
                    accumulator + value
                }
            println("The fold is $reduceResult")
        }
    }

    private fun collectFlow6() {
        val flow1 = flow{
            emit(1)
            delay(500)
            emit(2)
        }
        viewModelScope.launch(Dispatchers.IO) {
            flow1.flatMapConcat {
                flow {
                    emit(it + 1)
                    delay(500)
                    emit(it + 2)
                }
            }.collect{
                println("The value is $it")
            }
        }
    }


}