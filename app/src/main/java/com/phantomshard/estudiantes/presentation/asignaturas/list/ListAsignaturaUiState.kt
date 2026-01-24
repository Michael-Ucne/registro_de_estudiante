package com.phantomshard.estudiantes.presentation.asignaturas.list

import com.phantomshard.estudiantes.domain.model.Asignatura

data class ListAsignaturaUiState(
    val isLoading: Boolean = false,
    val asignaturas: List<Asignatura> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)
