package com.filimonov.todolist.presentation.createscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.filimonov.todolist.R
import com.filimonov.todolist.presentation.component.TodoDatePickerDialog
import com.filimonov.todolist.presentation.utils.toFullDateString
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CreateTopAppBar(
                onBackClick = onFinish
            )
        }
    ) { innerPadding ->
        val state by viewModel.state.collectAsState()
        when (val currentState = state) {
            is CreateState.Creation -> {
                Column(modifier = Modifier.padding(innerPadding)) {
                    InputTitle(
                        value = currentState.title,
                        onValueChange = { title ->
                            viewModel.processCommand(CreateCommand.InputTitle(title))
                        }
                    )
                    DateTimeSelectors(
                        selectedDay = currentState.selectedDay,
                        startTime = currentState.startTime,
                        finishTime = currentState.finishTime,
                        onDateSelected = { selectedDay ->
                            viewModel.processCommand(CreateCommand.InputDate(selectedDay))
                        },
                        onStartTimeSelected = { startTime ->
                            viewModel.processCommand(CreateCommand.InputStartTime(startTime))
                        },
                        onFinishTimeSelected = { finishTime ->
                            viewModel.processCommand(CreateCommand.InputFinishTime(finishTime))
                        }
                    )
                    InputDescription(
                        modifier = Modifier.weight(1f),
                        value = currentState.description,
                        onValueChange = { description ->
                            viewModel.processCommand(CreateCommand.InputDescription(description))

                        }
                    )
                    SaveButton(
                        enabled = currentState.isSaveEnabled,
                        onClick = {
                            viewModel.processCommand(CreateCommand.Save)
                        }
                    )
                }
            }

            CreateState.Finish -> onFinish()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(R.string.create_todo))
        },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBackClick()
                    }
                    .padding(8.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.navigation_back)
            )
        }
    )
}

@Composable
private fun InputTitle(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
        ),
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.todo_name_placeholder),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            )
        }
    )
}

@Composable
private fun InputDescription(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
        ),
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.todo_description_placeholder),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            )
        }
    )
}

@Composable
private fun SaveButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.save))
    }
}

@Composable
private fun DateTimeSelectors(
    modifier: Modifier = Modifier,
    selectedDay: LocalDate,
    startTime: LocalTime,
    finishTime: LocalTime,
    onDateSelected: (LocalDate) -> Unit,
    onStartTimeSelected: (LocalTime) -> Unit,
    onFinishTimeSelected: (LocalTime) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showFinishTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        TextButton(
            onClick = {
                showDatePicker = true
            }
        ) {
            Text(
                text = stringResource(R.string.date, selectedDay.toFullDateString()),
                fontSize = 17.sp
            )
        }

        Row {
            TextButton(
                onClick = {
                    showStartTimePicker = true
                }
            ) {
                Text(
                    text = stringResource(
                        R.string.select_time_start,
                        "%02d:%02d".format(startTime.hour, startTime.minute)
                    ),
                    fontSize = 17.sp
                )
            }
            TextButton(
                onClick = {
                    showFinishTimePicker = true
                }
            ) {
                Text(
                    text = stringResource(
                        R.string.select_time_finish,
                        "%02d:%02d".format(finishTime.hour, finishTime.minute)
                    ),
                    fontSize = 17.sp
                )
            }
        }
    }

    if (showDatePicker) {
        TodoDatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                onDateSelected(date)
                showDatePicker = false
            }
        )
    }

    if (showStartTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showStartTimePicker = false },
            onTimeSelected = { hour, minute ->
                val selectedStartTime = LocalTime.of(hour, minute)
                onStartTimeSelected(selectedStartTime)
                showStartTimePicker = false
            },
            time = startTime
        )
    }

    if (showFinishTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showFinishTimePicker = false },
            onTimeSelected = { hour, minute ->
                val selectedFinishTime = LocalTime.of(hour, minute)
                onFinishTimeSelected(selectedFinishTime)
                showFinishTimePicker = false
            },
            time = finishTime
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    time: LocalTime,
) {
    val timePickerState =
        rememberTimePickerState(initialHour = time.hour, initialMinute = time.minute)

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        TimePicker(
            state = timePickerState,
            modifier = Modifier.padding(16.dp)
        )
    }
}
