package com.phantomshard.estudiantes.presentation.asignaturas.list

sealed class ListAsignaturaUiEvent {
    data object Load : ListAsignaturaUiEvent()
    data object Refresh : ListAsignaturaUiEvent()
    data class Delete(val id: Int) : ListAsignaturaUiEvent()
    data class ShowMessage(val message: String) : ListAsignaturaUiEvent()
    data object ClearMessage : ListAsignaturaUiEvent()
    data object CreateNew : ListAsignaturaUiEvent()
    data class Edit(val id: Int) : ListAsignaturaUiEvent()
    data object NavigationDone : ListAsignaturaUiEvent()
}
