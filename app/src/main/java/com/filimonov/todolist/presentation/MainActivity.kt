package com.filimonov.todolist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.filimonov.todolist.presentation.navigation.NavGraph
import com.filimonov.todolist.presentation.ui.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                NavGraph()
            }
        }
    }
}
