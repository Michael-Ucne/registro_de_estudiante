package com.phantomshard.estudiantes.presentation.estudiantes.list

sealed class ListEstudianteUiEvent {
    data object Load : ListEstudianteUiEvent()
    data object Refresh : ListEstudianteUiEvent()
    data class Delete(val id: Int) : ListEstudianteUiEvent()
    data class ShowMessage(val message: String) : ListEstudianteUiEvent()
    data object ClearMessage : ListEstudianteUiEvent()
    data object CreateNew : ListEstudianteUiEvent()
    data class Edit(val id: Int) : ListEstudianteUiEvent()
    data object NavigationDone : ListEstudianteUiEvent()
}