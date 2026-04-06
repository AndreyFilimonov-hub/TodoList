package com.filimonov.todolist.domain.usecase

import com.filimonov.todolist.domain.entity.Todo
import com.filimonov.todolist.domain.repository.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(private val repository: TodoRepository) {

    suspend operator fun invoke(todo: Todo) {
        return repository.addTodo(todo)
    }
}