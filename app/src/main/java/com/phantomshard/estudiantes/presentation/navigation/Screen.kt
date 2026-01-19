package com.phantomshard.estudiantes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TaskList : Screen()

    @Serializable
    data class EditTask(val taskId: Int) : Screen()
}
