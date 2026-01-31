package com.phantomshard.estudiantes.presentation.penalidades.list

import com.phantomshard.estudiantes.domain.model.TipoPenalidad

data class ListTipoPenalidadUiState(
    val tipos: List<TipoPenalidad> = emptyList(),
    val isLoading: Boolean = false,
    val navigateToEditId: Int? = null,
    val navigateToCreate: Boolean = false,
    val message: String? = null
)
