package com.phantomshard.estudiantes.presentation.estudiantes.list

import com.phantomshard.estudiantes.domain.model.Estudiante

data class ListEstudianteUiState(
    val isLoading: Boolean = false,
    val tasks: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)