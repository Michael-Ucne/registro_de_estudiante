package com.phantomshard.estudiantes.presentation.asignaturas.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phantomshard.estudiantes.domain.model.Asignatura
import com.phantomshard.estudiantes.domain.usecase.asignaturas.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val asignaturaId: Int = savedStateHandle["asignaturaId"] ?: 0

    private val _state = MutableStateFlow(EditAsignaturaUiState())
    val state: StateFlow<EditAsignaturaUiState> = _state.asStateFlow()

    init {
        loadAsignatura(asignaturaId)
    }

    fun onEvent(event: EditAsignaturaUiEvent) {
        when (event) {
            is EditAsignaturaUiEvent.CodigoChanged -> _state.update { it.copy(codigo = event.value, codigoError = null) }
            is EditAsignaturaUiEvent.NombreChanged -> _state.update { it.copy(nombreAsignatura = event.value, nombreError = null) }
            is EditAsignaturaUiEvent.AulaChanged -> _state.update { it.copy(aula = event.value, aulaError = null) }
            is EditAsignaturaUiEvent.CreditosChanged -> _state.update { it.copy(creditos = event.value, creditosError = null) }
            EditAsignaturaUiEvent.Save -> onSave()
            EditAsignaturaUiEvent.Delete -> onDelete()
        }
    }

    private fun loadAsignatura(id: Int) {
        if (id == 0) {
            _state.update { it.copy(isNew = true, asignaturaId = null) }
            return
        }

        viewModelScope.launch {
            getAsignaturaUseCase(id)?.let { asignatura ->
                _state.update {
                    it.copy(
                        isNew = false,
                        asignaturaId = asignatura.asignaturaId,
                        codigo = asignatura.codigo,
                        nombreAsignatura = asignatura.nombre,
                        aula = asignatura.aula,
                        creditos = asignatura.creditos.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val s = state.value
        val vCodigo = validateCodigo(s.codigo)
        val vNombre = validateNombreAsignatura(s.nombreAsignatura)
        val vAula = validateAula(s.aula)
        val vCreditos = validateCreditos(s.creditos)

        if (!vCodigo.isValid || !vNombre.isValid || !vAula.isValid || !vCreditos.isValid) {
            _state.update {
                it.copy(
                    codigoError = vCodigo.error,
                    nombreError = vNombre.error,
                    aulaError = vAula.error,
                    creditosError = vCreditos.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val result = upsertAsignaturaUseCase(
                Asignatura(
                    asignaturaId = s.asignaturaId ?: 0,
                    codigo = s.codigo,
                    nombre = s.nombreAsignatura,
                    aula = s.aula,
                    creditos = s.creditos.toInt()
                )
            )
            result.onSuccess {
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false, errorMessage = e.message) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.asignaturaId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteAsignaturaUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}
