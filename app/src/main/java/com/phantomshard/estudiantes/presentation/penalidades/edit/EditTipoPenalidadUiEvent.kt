package com.phantomshard.estudiantes.presentation.penalidades.edit

sealed class EditTipoPenalidadUiEvent {
    data class NombreChanged(val value: String) : EditTipoPenalidadUiEvent()
    data class DescripcionChanged(val value: String) : EditTipoPenalidadUiEvent()
    data class PuntosChanged(val value: String) : EditTipoPenalidadUiEvent()
    data object Save : EditTipoPenalidadUiEvent()
    data object Delete : EditTipoPenalidadUiEvent()
}
