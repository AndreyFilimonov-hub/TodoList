package com.filimonov.todolist.domain.repository

import com.filimonov.todolist.domain.entity.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodoRepository {

    fun getAllTodoForDate(date: LocalDate): Flow<List<Todo>>

    suspend fun addTodo(name: String, description: String, startDate: Long, finishDate: Long)
}