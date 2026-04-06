package com.filimonov.todolist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val json: String,
    val startDate: Long,
    val finishDate: Long
)
