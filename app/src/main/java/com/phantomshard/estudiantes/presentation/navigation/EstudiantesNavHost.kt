package com.phantomshard.estudiantes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.phantomshard.estudiantes.presentation.edit.EditTaskScreen
import com.phantomshard.estudiantes.presentation.list.ListTaskScreen
import com.phantomshard.estudiantes.presentation.asignaturas.list.ListAsignaturaScreen
import com.phantomshard.estudiantes.presentation.asignaturas.edit.EditAsignaturaScreen

@Composable
fun EstudiantesNavHost(
    navHostController: NavHostController,
    onOpenDrawer: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TaskList
    ) {
        composable<Screen.TaskList> {
            ListTaskScreen(
                onOpenDrawer = onOpenDrawer,
                navigateToEditTask = { taskId ->
                    navHostController.navigate(Screen.EditTask(taskId)) {
                        launchSingleTop = true
                    }
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

        composable<Screen.AsignaturaList> {
            ListAsignaturaScreen(
                onOpenDrawer = onOpenDrawer,
                onNavigateToEdit = { id ->
                    navHostController.navigate(Screen.EditAsignatura(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Screen.EditAsignatura> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditAsignatura>()
            EditAsignaturaScreen(
                asignaturaId = args.asignaturaId,
                onBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
