package com.phantomshard.estudiantes.presentation.edit

sealed interface EditTaskUiEvent {
    data class Load(val id: Int?) : EditTaskUiEvent
    data class NombreChanged(val value: String) : EditTaskUiEvent
    data class EmailChanged(val value: String) : EditTaskUiEvent
    data class EdadChanged(val value: String) : EditTaskUiEvent
    data object Save : EditTaskUiEvent
    data object Delete : EditTaskUiEvent
}