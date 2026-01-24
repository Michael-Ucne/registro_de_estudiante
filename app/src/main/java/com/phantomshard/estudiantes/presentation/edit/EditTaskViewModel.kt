package com.phantomshard.estudiantes.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.phantomshard.estudiantes.domain.model.Task
import com.phantomshard.estudiantes.domain.usecase.DeleteTaskUseCase
import com.phantomshard.estudiantes.domain.usecase.GetTaskUseCase
import com.phantomshard.estudiantes.domain.usecase.UpsertTaskUseCase
import com.phantomshard.estudiantes.domain.usecase.validateNombre
import com.phantomshard.estudiantes.domain.usecase.validateEmail
import com.phantomshard.estudiantes.domain.usecase.validateEdad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Int = savedStateHandle["taskId"] ?: 0

    private val _state = MutableStateFlow(EditTaskUiState())
    val state: StateFlow<EditTaskUiState> = _state.asStateFlow()

    init {
        loadTask(taskId)
    }

    fun onEvent(event: EditTaskUiEvent) {
        when (event) {
            is EditTaskUiEvent.Load -> loadTask(event.id)
            is EditTaskUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }
            is EditTaskUiEvent.EmailChanged -> _state.update {
                it.copy(email = event.value, emailError = null)
            }
            is EditTaskUiEvent.EdadChanged -> _state.update {
                it.copy(edad = event.value, edadError = null)
            }
            EditTaskUiEvent.Save -> onSave()
            EditTaskUiEvent.Delete -> onDelete()
        }
    }

    private fun loadTask(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, estudianteId = null) }
            return
        }

        viewModelScope.launch {
            val task = getTaskUseCase(id)
            if (task != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        estudianteId = task.estudianteId,
                        nombre = task.nombre,
                        email = task.email,
                        edad = task.edad.toString()
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

            val task = Task(
                estudianteId = state.value.estudianteId ?: 0,
                nombre = nombre,
                email = email,
                edad = edad.toInt()
            )

            val result = upsertTaskUseCase(task)
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
            deleteTaskUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}