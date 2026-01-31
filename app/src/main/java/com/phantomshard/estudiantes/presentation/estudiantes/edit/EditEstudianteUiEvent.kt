package com.phantomshard.estudiantes.presentation.estudiantes.edit

sealed interface EditEstudianteUiEvent {
    data class Load(val id: Int?) : EditEstudianteUiEvent
    data class NombreChanged(val value: String) : EditEstudianteUiEvent
    data class EmailChanged(val value: String) : EditEstudianteUiEvent
    data class EdadChanged(val value: String) : EditEstudianteUiEvent
    data object Save : EditEstudianteUiEvent
    data object Delete : EditEstudianteUiEvent
}