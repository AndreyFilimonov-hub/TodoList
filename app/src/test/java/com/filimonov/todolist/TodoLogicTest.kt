package com.filimonov.todolist

import com.filimonov.todolist.domain.entity.Todo
import com.filimonov.todolist.presentation.utils.isTodoInHour
import com.filimonov.todolist.presentation.utils.toLong
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class TodoLogicTest {

    private val selectedDay = LocalDate.of(2026, 4, 7)

    @Test
    fun `task exactly 17 to 18 should be in 17 hour slot but not in 18`() {
        val todo = Todo(
            id = 1,
            name = "Test",
            description = "Test",
            startDate = selectedDay.atTime(17, 0).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli(),
            finishDate = selectedDay.atTime(18, 0).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
        )

        assertTrue(isTodoInHour(todo, selectedDay, 17))
        assertFalse(isTodoInHour(todo, selectedDay, 18))
    }

    @Test
    fun `task from 23 to 00-10 should correctly identify finish date as next day`() {
        val startTime = LocalTime.of(23, 0)
        val finishTime = LocalTime.of(0, 10)

        val finishDay = if (finishTime.isBefore(startTime)) {
            selectedDay.plusDays(1)
        } else {
            selectedDay
        }

        assertEquals(selectedDay.plusDays(1), finishDay)

        val finishDateMillis = finishTime.toLong(finishDay)
        val startDateMillis = startTime.toLong(selectedDay)

        assertTrue(finishDateMillis > startDateMillis)
    }
}