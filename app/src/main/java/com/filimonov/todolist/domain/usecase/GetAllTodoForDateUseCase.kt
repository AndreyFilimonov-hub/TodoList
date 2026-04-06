package com.filimonov.todolist.domain.usecase

import com.filimonov.todolist.domain.entity.Todo
import com.filimonov.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetAllTodoForDateUseCase(private val repository: TodoRepository) {

    operator fun invoke(date: LocalDate): Flow<List<Todo>> {
        return repository.getAllTodoForDate(date)
    }
}