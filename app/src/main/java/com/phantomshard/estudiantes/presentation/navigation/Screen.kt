package com.phantomshard.estudiantes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TaskList : Screen()

    @Serializable
    data class EditTask(val taskId: Int) : Screen()

    @Serializable
    data object AsignaturaList : Screen()

    @Serializable
    data class EditAsignatura(val asignaturaId: Int) : Screen()
}
