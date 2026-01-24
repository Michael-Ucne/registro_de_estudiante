package com.phantomshard.estudiantes.presentation.list

sealed class ListTaskUiEvent {
    data object Load : ListTaskUiEvent()
    data object Refresh : ListTaskUiEvent()
    data class Delete(val id: Int) : ListTaskUiEvent()
    data class ShowMessage(val message: String) : ListTaskUiEvent()
    data object ClearMessage : ListTaskUiEvent()
    data object CreateNew : ListTaskUiEvent()
    data class Edit(val id: Int) : ListTaskUiEvent()
    data object NavigationDone : ListTaskUiEvent()
}