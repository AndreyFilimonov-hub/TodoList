package com.filimonov.todolist.presentation.createscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.todolist.domain.entity.Todo
import com.filimonov.todolist.domain.usecase.AddTodoUseCase
import com.filimonov.todolist.presentation.utils.toLong
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreateState>(CreateState.Creation())
    val state = _state.asStateFlow()

    fun processCommand(command: CreateCommand) {
        viewModelScope.launch {
            when (command) {
                is CreateCommand.InputTitle -> {
                    _state.update { previousState ->
                        if (previousState is CreateState.Creation) {
                            previousState.copy(title = command.title)
                        } else {
                            previousState
                        }
                    }
                }

                is CreateCommand.InputDescription -> {
                    _state.update { previousState ->
                        if (previousState is CreateState.Creation) {
                            previousState.copy(description = command.description)
                        } else {
                            previousState
                        }
                    }
                }

                is CreateCommand.InputDate -> {
                    _state.update { previousState ->
                        if (previousState is CreateState.Creation) {
                            previousState.copy(selectedDay = command.selectedDate)
                        } else {
                            previousState
                        }
                    }
                }

                is CreateCommand.InputStartTime -> {
                    _state.update { previousState ->
                        if (previousState is CreateState.Creation) {
                            previousState.copy(startTime = command.startTime)
                        } else {
                            previousState
                        }
                    }
                }

                is CreateCommand.InputFinishTime -> {
                    _state.update { previousState ->
                        if (previousState is CreateState.Creation) {
                            previousState.copy(finishTime = command.finishTime)
                        } else {
                            previousState
                        }
                    }
                }

                CreateCommand.Back -> {
                    _state.update { CreateState.Finish }
                }

                CreateCommand.Save -> {
                    _state.update { previousState ->
                        if (previousState is CreateState.Creation) {
                            val selectedDay = previousState.selectedDay
                            val startTime = previousState.startTime
                            val finishTime = previousState.finishTime

                            val startDate = startTime.toLong(selectedDay)

                            val finishDay = if (finishTime.isBefore(startTime) || finishTime == startTime) {
                                selectedDay.plusDays(1)
                            } else {
                                selectedDay
                            }

                            val finishDate = finishTime.toLong(finishDay)

                            val todo = Todo(
                                id = 0,
                                name = previousState.title,
                                description = previousState.description,
                                startDate = startDate,
                                finishDate = finishDate
                            )
                            addTodoUseCase(todo)
                            CreateState.Finish
                        } else {
                            previousState
                        }
                    }
                }
            }
        }
    }
}