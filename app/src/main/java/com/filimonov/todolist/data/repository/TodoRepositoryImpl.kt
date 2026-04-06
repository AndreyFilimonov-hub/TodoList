package com.filimonov.todolist.data.repository

import com.filimonov.todolist.data.dao.TodoDao
import com.filimonov.todolist.data.mapper.getEndDayTimeMillis
import com.filimonov.todolist.data.mapper.getStartDayTimeMillis
import com.filimonov.todolist.data.mapper.toDbModel
import com.filimonov.todolist.data.mapper.toEntities
import com.filimonov.todolist.domain.entity.Todo
import com.filimonov.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override fun getAllTodoForDate(date: LocalDate): Flow<List<Todo>> {
        return todoDao.getAllTodoForDate(date.getStartDayTimeMillis(), date.getEndDayTimeMillis())
            .map { it.toEntities() }
    }

    override suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo.toDbModel())
    }
}