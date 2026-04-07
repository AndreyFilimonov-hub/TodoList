package com.filimonov.todolist.data.mapper

import com.filimonov.todolist.data.model.TodoDbModel
import com.filimonov.todolist.data.model.TodoDto
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
    val todoDto = TodoDto(
        id = id,
        name = name,
        description = description,
        startDate = startDate,
        finishDate = finishDate
    )
    val json = Json.encodeToString(todoDto)
    return TodoDbModel(
        id = 0,
        json = json,
        startDate = startDate,
        finishDate = finishDate
    )
}

fun TodoDbModel.toEntity(): Todo {
    val todoDto = Json.decodeFromString<TodoDto>(this.json)
    return Todo(
        id = id,
        name = todoDto.name,
        description = todoDto.description,
        startDate = todoDto.startDate,
        finishDate = todoDto.finishDate
    )
}

fun List<TodoDbModel>.toEntities(): List<Todo> {
    return this.map { it.toEntity() }
}