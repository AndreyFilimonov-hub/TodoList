package com.filimonov.todolist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("date_start") val startDate: Long,
    @SerialName("date_finish") val finishDate: Long
)