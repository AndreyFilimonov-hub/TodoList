package com.filimonov.todolist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.filimonov.todolist.data.model.TodoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("""
        SELECT * FROM todos
        WHERE (startDate >= :dayStart AND startDate <= :dayEnd)
            OR (finishDate >= :dayStart AND finishDate <= :dayEnd)
    """)
    fun getAllTodoForDate(dayStart: Long, dayEnd: Long): Flow<List<TodoDbModel>>

    @Insert
    suspend fun addTodo(todoDbModel: TodoDbModel)
}