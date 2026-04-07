package com.filimonov.todolist.presentation.createscreen

import java.time.LocalDate
import java.time.LocalTime

sealed interface CreateState {

    data class Creation(
        val title: String = "",
        val description: String = "",
        val selectedDay: LocalDate = LocalDate.now(),
        val startTime: LocalTime = LocalTime.now().plusHours(1).withMinute(0),
        val finishTime: LocalTime = startTime.plusHours(1),
    ) : CreateState {
        val isSaveEnabled: Boolean
            get() = title.isNotBlank() && description.isNotBlank()
    }

    data object Finish : CreateState
}