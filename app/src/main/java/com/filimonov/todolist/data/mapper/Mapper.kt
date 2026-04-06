package com.filimonov.todolist.data.mapper

import com.filimonov.todolist.data.model.TodoDbModel
import com.filimonov.todolist.domain.entity.Todo
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

fun LocalDate.getStartDayTimeMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun LocalDate.getEndDayTimeMillis(): Long {
    return this.atTime(LocalTime.MAX)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun Todo.toDbModel(): TodoDbModel {
    val json = Json.encodeToString(this)
    return TodoDbModel(
        id = 0,
        json = json,
        startDate = startDate,
        finishDate = finishDate
    )
}

fun TodoDbModel.toEntity(): Todo {
    val todo = Json.decodeFromString<Todo>(this.json)
    return todo.copy(id = this.id)
}

fun List<TodoDbModel>.toEntities(): List<Todo> {
    return this.map { it.toEntity() }
}