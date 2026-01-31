package com.phantomshard.estudiantes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.phantomshard.estudiantes.presentation.estudiantes.edit.EditEstudianteScreen
import com.phantomshard.estudiantes.presentation.estudiantes.list.ListEstudianteScreen
import com.phantomshard.estudiantes.presentation.asignaturas.list.ListAsignaturaScreen
import com.phantomshard.estudiantes.presentation.asignaturas.edit.EditAsignaturaScreen
import com.phantomshard.estudiantes.presentation.penalidades.list.ListTipoPenalidadScreen
import com.phantomshard.estudiantes.presentation.penalidades.edit.EditTipoPenalidadScreen

@Composable
fun EstudiantesNavHost(
    navHostController: NavHostController,
    onOpenDrawer: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.EstudianteList
    ) {
        composable<Screen.EstudianteList> {
            ListEstudianteScreen(
                onOpenDrawer = onOpenDrawer,
                navigateToEditEstudiante = { estudianteId ->
                    navHostController.navigate(Screen.EditEstudiante(estudianteId)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable<Screen.EditEstudiante> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditEstudiante>()
            EditEstudianteScreen(
                estudianteId = args.estudianteId,
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

        composable<Screen.TipoPenalidadList> {
            ListTipoPenalidadScreen(
                onOpenDrawer = onOpenDrawer,
                navigateToEdit = { id ->
                    navHostController.navigate(Screen.EditTipoPenalidad(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Screen.EditTipoPenalidad> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.EditTipoPenalidad>()
            EditTipoPenalidadScreen(
                tipoId = args.tipoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
