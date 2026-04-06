package com.filimonov.todolist.domain.entity

data class Todo(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: Long,
    val finishDate: Long
)