package com.phantomshard.estudiantes.presentation.penalidades.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phantomshard.estudiantes.domain.use_cases.tipopenalidad.DeleteTipoPenalidadUseCase
import com.phantomshard.estudiantes.domain.use_cases.tipopenalidad.ObserveTiposPenalidadesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    private val observeTipos: ObserveTiposPenalidadesUseCase,
    private val deleteTipo: DeleteTipoPenalidadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListTipoPenalidadUiState())
    val state = _state.asStateFlow()

    init {
        observeTipos()
            .onEach { list ->
                _state.update { it.copy(tipos = list, isLoading = false) }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: ListTipoPenalidadUiEvent) {
        when (event) {
            is ListTipoPenalidadUiEvent.Delete -> {
                viewModelScope.launch {
                    deleteTipo(event.id)
                }
            }
            is ListTipoPenalidadUiEvent.Edit -> {
                _state.update { it.copy(navigateToEditId = event.id) }
            }
            ListTipoPenalidadUiEvent.CreateNew -> {
                _state.update { it.copy(navigateToCreate = true) }
            }
            ListTipoPenalidadUiEvent.NavigationDone -> {
                _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
            }
            ListTipoPenalidadUiEvent.ClearMessage -> {
                _state.update { it.copy(message = null) }
            }
        }
    }
}
