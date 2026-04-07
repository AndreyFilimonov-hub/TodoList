package com.filimonov.todolist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filimonov.todolist.presentation.createscreen.CreateRoute
import com.filimonov.todolist.presentation.createscreen.CreateScreen
import com.filimonov.todolist.presentation.homescreen.HomeRoute
import com.filimonov.todolist.presentation.homescreen.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen(
                onAddTodoClick = {
                    navController.navigate(CreateRoute)
                }
            )
        }
        composable<CreateRoute> {
            CreateScreen(
                onFinish = {
                    navController.safePopBackStack()
                }
            )
        }
    }
}

private fun NavController.safePopBackStack(): Boolean {
    return if (previousBackStackEntry != null) {
        popBackStack()
    } else {
        false
    }
}