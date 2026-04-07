package com.filimonov.todolist.presentation.homescreen

import java.time.LocalDate

sealed interface HomeCommand {

    data class SelectDay(val day: LocalDate): HomeCommand
}