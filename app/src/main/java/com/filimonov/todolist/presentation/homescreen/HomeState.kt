package com.filimonov.todolist.presentation.homescreen

import com.filimonov.todolist.domain.entity.Todo
import java.time.LocalDate

sealed interface HomeState {

    data object Loading: HomeState

    data class Content(
        val todos: List<Todo>,
        val selectedDay: LocalDate
    ) : HomeState
}
