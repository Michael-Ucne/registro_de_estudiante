package com.phantomshard.estudiantes.presentation.asignaturas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phantomshard.estudiantes.domain.usecase.DeleteAsignaturaUseCase
import com.phantomshard.estudiantes.domain.usecase.ObserveAsignaturasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAsignaturaViewModel @Inject constructor(
    private val observeAsignaturasUseCase: ObserveAsignaturasUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListAsignaturaUiState(isLoading = true))
    val state: StateFlow<ListAsignaturaUiState> = _state.asStateFlow()

    init {
        loadAsignaturas()
    }

    fun onEvent(event: ListAsignaturaUiEvent) {
        when (event) {
            ListAsignaturaUiEvent.Load -> loadAsignaturas()
            ListAsignaturaUiEvent.Refresh -> loadAsignaturas()
            is ListAsignaturaUiEvent.Delete -> onDelete(event.id)
            is ListAsignaturaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            ListAsignaturaUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            ListAsignaturaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListAsignaturaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            ListAsignaturaUiEvent.NavigationDone -> _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
        }
    }

    private fun loadAsignaturas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeAsignaturasUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, asignaturas = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteAsignaturaUseCase(id)
            onEvent(ListAsignaturaUiEvent.ShowMessage("Asignatura eliminada"))
        }
    }
}
