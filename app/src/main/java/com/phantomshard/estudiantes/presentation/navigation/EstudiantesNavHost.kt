package com.phantomshard.estudiantes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.phantomshard.estudiantes.presentation.edit.EditTaskScreen
import com.phantomshard.estudiantes.presentation.list.ListTaskScreen

@Composable
fun EstudiantesNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TaskList
    ) {
        composable<Screen.TaskList> {
            ListTaskScreen(
                navigateToEditTask = { taskId ->
                    navHostController.navigate(Screen.EditTask(taskId))
                }
            )
        }
        
        composable<Screen.EditTask> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditTask>()
            EditTaskScreen(
                taskId = args.taskId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
