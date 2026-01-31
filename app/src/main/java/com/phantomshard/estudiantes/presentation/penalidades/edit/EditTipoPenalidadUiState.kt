package com.phantomshard.estudiantes.presentation.penalidades.edit

data class EditTipoPenalidadUiState(
    val nombre: String = "",
    val descripcion: String = "",
    val puntos: String = "",
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val puntosError: String? = null,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val errorMessage: String? = null
)
