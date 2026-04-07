@file:OptIn(ExperimentalCoroutinesApi::class)

package com.filimonov.todolist.presentation.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.todolist.domain.usecase.GetAllTodoForDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllTodoForDateUseCase: GetAllTodoForDateUseCase) :
    ViewModel() {

    private val _selectedDay = MutableStateFlow(LocalDate.now())

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    init {
        _selectedDay
            .flatMapLatest { day ->
                getAllTodoForDateUseCase(day).map { todos ->
                    Log.d("AAA", todos.toString())
                    HomeState.Content(todos, day)
                }
            }
            .onEach { newState ->
                _state.value = newState
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: HomeCommand) {
        when (command) {
            is HomeCommand.SelectDay -> {
                _selectedDay.update { command.day }
            }
        }
    }
}