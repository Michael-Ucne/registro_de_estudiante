package com.phantomshard.estudiantes.presentation.estudiantes.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.phantomshard.estudiantes.domain.model.Estudiante
import com.phantomshard.estudiantes.domain.usecase.estudiantes.DeleteEstudianteUseCase
import com.phantomshard.estudiantes.domain.usecase.estudiantes.GetEstudianteUseCase
import com.phantomshard.estudiantes.domain.usecase.estudiantes.UpsertEstudianteUseCase
import com.phantomshard.estudiantes.domain.usecase.estudiantes.validateNombre
import com.phantomshard.estudiantes.domain.usecase.estudiantes.validateEmail
import com.phantomshard.estudiantes.domain.usecase.estudiantes.validateEdad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val estudianteId: Int = savedStateHandle["estudianteId"] ?: 0

    private val _state = MutableStateFlow(EditEstudianteUiState())
    val state: StateFlow<EditEstudianteUiState> = _state.asStateFlow()

    init {
        loadEstudiante(estudianteId)
    }

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.Load -> loadEstudiante(event.id)
            is EditEstudianteUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }
            is EditEstudianteUiEvent.EmailChanged -> _state.update {
                it.copy(email = event.value, emailError = null)
            }
            is EditEstudianteUiEvent.EdadChanged -> _state.update {
                it.copy(edad = event.value, edadError = null)
            }
            EditEstudianteUiEvent.Save -> onSave()
            EditEstudianteUiEvent.Delete -> onDelete()
        }
    }

    private fun loadEstudiante(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, estudianteId = null) }
            return
        }

        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            if (estudiante != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        estudianteId = estudiante.estudianteId,
                        nombre = estudiante.nombre,
                        email = estudiante.email,
                        edad = estudiante.edad.toString()
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, estudianteId = null) }
            }
        }
    }

    private fun onSave() {
        val nombre = state.value.nombre
        val email = state.value.email
        val edad = state.value.edad
        
        val nombreValidation = validateNombre(nombre)
        val emailValidation = validateEmail(email)
        val edadValidation = validateEdad(edad)

        if (!nombreValidation.isValid || !emailValidation.isValid || !edadValidation.isValid) {
            _state.update {
                it.copy(
                    nombreError = nombreValidation.error,
                    emailError = emailValidation.error,
                    edadError = edadValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val estudiante = Estudiante(
                estudianteId = state.value.estudianteId ?: 0,
                nombre = nombre,
                email = email,
                edad = edad.toInt()
            )

            val result = upsertEstudianteUseCase(estudiante)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }
            }.onFailure { exception ->
                _state.update { it.copy(isSaving = false, errorMessage = exception.message) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.estudianteId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEstudianteUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}