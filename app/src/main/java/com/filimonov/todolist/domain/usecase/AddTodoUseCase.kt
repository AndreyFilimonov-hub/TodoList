package com.filimonov.todolist.domain.usecase

import com.filimonov.todolist.domain.repository.TodoRepository

class AddTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(
        name: String,
        description: String,
        startDate: Long,
        finishDate: Long
    ) {
        return repository.addTodo(name, description, startDate, finishDate)
    }
}