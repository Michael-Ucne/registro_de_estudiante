package com.phantomshard.estudiantes.presentation.list

import com.phantomshard.estudiantes.domain.model.Task

data class ListTaskUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)