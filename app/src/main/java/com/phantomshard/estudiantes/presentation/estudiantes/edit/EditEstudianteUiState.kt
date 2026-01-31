package com.phantomshard.estudiantes.presentation.estudiantes.edit

data class EditEstudianteUiState(
    val estudianteId: Int? = null,
    val nombre: String = "",
    val email: String = "",
    val edad: String = "",
    val nombreError: String? = null,
    val emailError: String? = null,
    val edadError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val errorMessage: String? = null
)