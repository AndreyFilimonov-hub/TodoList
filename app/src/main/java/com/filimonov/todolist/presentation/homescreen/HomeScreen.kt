package com.filimonov.todolist.presentation.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.filimonov.todolist.R
import com.filimonov.todolist.domain.entity.Todo
import com.filimonov.todolist.presentation.component.TodoDatePickerDialog
import com.filimonov.todolist.presentation.utils.toDayMonthString
import com.filimonov.todolist.presentation.utils.toHourFormat
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onAddTodoClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopAppBar(
                onDaySelected = { day ->
                    viewModel.processCommand(HomeCommand.SelectDay(day))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.clip(CircleShape),
                onClick = onAddTodoClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_todo)
                )
            }
        }
    ) { innerPadding ->
        val state by viewModel.state.collectAsState()
        when (val currentState = state) {
            HomeState.Loading -> Loading()
            is HomeState.Content -> {
                HomeContent(
                    selectedDay = currentState.selectedDay,
                    todos = currentState.todos,
                    contentPadding = innerPadding
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    selectedDay: LocalDate,
    todos: List<Todo>,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        item {
            Text(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                text = selectedDay.toDayMonthString(),
                fontSize = 24.sp
            )
        }
        items(
            count = 24,
            key = { it }
        ) { hour ->
            val todosInThisHour = todos.filter { todo ->
                isTodoInHour(todo, selectedDay, hour)
            }

            HourCell(hour = hour, todosInThisHour = todosInThisHour)
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onDaySelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        TodoDatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                onDaySelected(date)
                showDatePicker = false
            }
        )
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(R.string.todo_list))
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        showDatePicker = true
                    }
                    .padding(8.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.date_picker)
            )
        }
    )
}

@Composable
private fun HourCell(
    modifier: Modifier = Modifier,
    hour: Int,
    todosInThisHour: List<Todo>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 32.dp)
            .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.hour_format).format(hour))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            todosInThisHour.fastForEach { todo ->
                TodoCard(todo = todo)
            }
        }
    }
}

@Composable
private fun TodoCard(
    modifier: Modifier = Modifier,
    todo: Todo
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = todo.name,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${todo.startDate.toHourFormat()} - ${todo.finishDate.toHourFormat()}",
                    maxLines = 1,
                    softWrap = false
                )
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            isExpanded = !isExpanded
                        },
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = todo.description
                    )
                }
            }
        }
    }
}

private fun isTodoInHour(todo: Todo, selectedDay: LocalDate, hour: Int): Boolean {
    val hourStart = selectedDay.atTime(hour, 0)
        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val hourEnd = selectedDay.atTime(hour, 0).plusHours(1)
        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    return todo.startDate < hourEnd && todo.finishDate > hourStart
}

@Composable
@Preview
private fun HomePreview() {
    HomeScreen(
        onAddTodoClick = {}
    )
}
