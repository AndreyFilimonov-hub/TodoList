package com.filimonov.todolist.presentation.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val dayMonthFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM")

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