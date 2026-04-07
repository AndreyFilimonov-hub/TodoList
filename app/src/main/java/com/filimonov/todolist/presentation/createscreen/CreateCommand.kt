package com.filimonov.todolist.presentation.createscreen

import java.time.LocalDate
import java.time.LocalTime

sealed interface CreateCommand {

    data class InputTitle(val title: String): CreateCommand

    data class InputDescription(val description: String): CreateCommand

    data class InputDate(val selectedDate: LocalDate): CreateCommand

    data class InputStartTime(val startTime: LocalTime): CreateCommand

    data class InputFinishTime(val finishTime: LocalTime): CreateCommand

    data object Save : CreateCommand

    data object Back : CreateCommand
}