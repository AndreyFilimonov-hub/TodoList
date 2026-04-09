package com.filimonov.todolist.presentation.utils

import com.filimonov.todolist.domain.entity.Todo
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val dayMonthFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM")
private val fullDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

fun Long.toHourFormat(): String {
    val instant = Instant.ofEpochMilli(this)
    val time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime()
    return time.format(hourFormatter)
}

fun Long.toDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun LocalDate.toDayMonthString(): String {
    return this.format(dayMonthFormatter)
}

fun LocalDate.toFullDateString(): String {
    return this.format(fullDateFormatter)
}

fun LocalTime.toLong(date: LocalDate): Long {
    return date.atTime(this.hour, this.minute, 0, 0)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun isTodoInHour(todo: Todo, selectedDay: LocalDate, hour: Int): Boolean {
    val hourStart = selectedDay.atTime(hour, 0)
        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val hourEnd = selectedDay.atTime(hour, 0).plusHours(1)
        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    return todo.startDate < hourEnd && todo.finishDate > hourStart
}
