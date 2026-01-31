package com.phantomshard.estudiantes.presentation.penalidades.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phantomshard.estudiantes.domain.model.PenalidadValidation
import com.phantomshard.estudiantes.domain.model.TipoPenalidad
import com.phantomshard.estudiantes.domain.use_cases.tipopenalidad.DeleteTipoPenalidadUseCase
import com.phantomshard.estudiantes.domain.use_cases.tipopenalidad.GetTipoPenalidadUseCase
import com.phantomshard.estudiantes.domain.use_cases.tipopenalidad.UpsertTipoPenalidadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTipoPenalidadViewModel @Inject constructor(
    private val getTipo: GetTipoPenalidadUseCase,
    private val upsertTipo: UpsertTipoPenalidadUseCase,
    private val deleteTipo: DeleteTipoPenalidadUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(EditTipoPenalidadUiState())
    val state = _state.asStateFlow()

    private val validation = PenalidadValidation()
    private var currentId: Int = 0

    init {
        savedStateHandle.get<Int>("tipoId")?.let { id ->
            if (id > 0) {
                currentId = id
                _state.update { it.copy(isNew = false) }
                viewModelScope.launch {
                    getTipo(id)?.let { tipo ->
                        _state.update { it.copy(
                            nombre = tipo.nombre,
                            descripcion = tipo.descripcion,
                            puntos = tipo.puntosDescuento.toString()
                        ) }
                    }
                }
            }
        }
    }

    fun onEvent(event: EditTipoPenalidadUiEvent) {
        when (event) {
            is EditTipoPenalidadUiEvent.NombreChanged -> {
                _state.update { it.copy(nombre = event.value, nombreError = null) }
            }
            is EditTipoPenalidadUiEvent.DescripcionChanged -> {
                _state.update { it.copy(descripcion = event.value, descripcionError = null) }
            }
            is EditTipoPenalidadUiEvent.PuntosChanged -> {
                _state.update { it.copy(puntos = event.value, puntosError = null) }
            }
            EditTipoPenalidadUiEvent.Save -> {
                val errors = validation.validate(_state.value.nombre, _state.value.puntos)
                if (errors.isNotEmpty()) {
                    _state.update { it.copy(
                        nombreError = errors["nombre"],
                        puntosError = errors["puntos"]
                    ) }
                    return
                }

                viewModelScope.launch {
                    try {
                        val tipo = TipoPenalidad(
                            tipoId = currentId,
                            nombre = _state.value.nombre,
                            descripcion = _state.value.descripcion,
                            puntosDescuento = _state.value.puntos.toInt()
                        )
                        upsertTipo(tipo)
                        _state.update { it.copy(saved = true) }
                    } catch (e: Exception) {
                        _state.update { it.copy(errorMessage = e.message) }
                    }
                }
            }
            EditTipoPenalidadUiEvent.Delete -> {
                viewModelScope.launch {
                    try {
                        deleteTipo(currentId)
                        _state.update { it.copy(deleted = true) }
                    } catch (e: Exception) {
                        _state.update { it.copy(errorMessage = e.message) }
                    }
                }
            }
        }
    }
}
