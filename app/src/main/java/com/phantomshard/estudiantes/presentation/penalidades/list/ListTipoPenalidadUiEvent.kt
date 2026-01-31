package com.phantomshard.estudiantes.presentation.penalidades.list

sealed class ListTipoPenalidadUiEvent {
    data class Delete(val id: Int) : ListTipoPenalidadUiEvent()
    data class Edit(val id: Int) : ListTipoPenalidadUiEvent()
    data object CreateNew : ListTipoPenalidadUiEvent()
    data object NavigationDone : ListTipoPenalidadUiEvent()
    data object ClearMessage : ListTipoPenalidadUiEvent()
}
