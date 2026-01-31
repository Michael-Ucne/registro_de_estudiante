package com.phantomshard.estudiantes.presentation.estudiantes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.phantomshard.estudiantes.domain.usecase.estudiantes.DeleteEstudianteUseCase
import com.phantomshard.estudiantes.domain.usecase.estudiantes.ObserveEstudiantesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEstudianteViewModel @Inject constructor(
    private val observeEstudiantesUseCase: ObserveEstudiantesUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListEstudianteUiState(isLoading = true))
    val state: StateFlow<ListEstudianteUiState> = _state.asStateFlow()

    init {
        loadEstudiantes()
    }

    fun onEvent(event: ListEstudianteUiEvent) {
        when (event) {
            ListEstudianteUiEvent.Load -> loadEstudiantes()
            ListEstudianteUiEvent.Refresh -> loadEstudiantes()
            is ListEstudianteUiEvent.Delete -> onDelete(event.id)
            is ListEstudianteUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            ListEstudianteUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            ListEstudianteUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListEstudianteUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            ListEstudianteUiEvent.NavigationDone -> _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
        }
    }

    fun loadEstudiantes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeEstudiantesUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, tasks = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteEstudianteUseCase(id)
            onEvent(ListEstudianteUiEvent.ShowMessage("Eliminado"))
        }
    }
}