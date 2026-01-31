package com.phantomshard.estudiantes.presentation.penalidades.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phantomshard.estudiantes.domain.model.TipoPenalidad

@Composable
fun ListTipoPenalidadScreen(
    onOpenDrawer: () -> Unit = {},
    navigateToEdit: (id: Int) -> Unit,
    viewModel: ListTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            navigateToEdit(0)
            viewModel.onEvent(ListTipoPenalidadUiEvent.NavigationDone)
        }
    }

    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let { id ->
            navigateToEdit(id)
            viewModel.onEvent(ListTipoPenalidadUiEvent.NavigationDone)
        }
    }

    ListTipoPenalidadBody(
        state = state,
        onEvent = viewModel::onEvent,
        onOpenDrawer = onOpenDrawer,
        navigateToEdit = navigateToEdit
    )
}

@Composable
fun ListTipoPenalidadBody(
    state: ListTipoPenalidadUiState,
    onEvent: (ListTipoPenalidadUiEvent) -> Unit,
    onOpenDrawer: () -> Unit,
    navigateToEdit: (id: Int) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(64.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "Tipos de Penalidades",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListTipoPenalidadUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_add"),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        snackbarHost = {
            state.message?.let { message ->
                Snackbar(
                    action = {
                        TextButton(onClick = { onEvent(ListTipoPenalidadUiEvent.ClearMessage) }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(message)
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).testTag("loading")
                )
            } else if (state.tipos.isEmpty()) {
                Text(
                    text = "No hay tipos de penalidades registrados",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp).testTag("penalidad_list")
                ) {
                    items(state.tipos) { item ->
                        TipoPenalidadCard(
                            item = item,
                            onEdit = { onEvent(ListTipoPenalidadUiEvent.Edit(item.tipoId)) },
                            onDelete = { onEvent(ListTipoPenalidadUiEvent.Delete(item.tipoId)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TipoPenalidadCard(
    item: TipoPenalidad,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).testTag("penalidad_card_${item.tipoId}"),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.nombre, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(item.descripcion, style = MaterialTheme.typography.bodyMedium)
                Text("Descuento: ${item.puntosDescuento} pts", style = MaterialTheme.typography.labelMedium)
            }
            IconButton(onClick = onEdit, modifier = Modifier.testTag("edit_button_${item.tipoId}")) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.secondary)
            }
            IconButton(onClick = onDelete, modifier = Modifier.testTag("delete_button_${item.tipoId}")) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTipoPenalidadScreenPreview() {
    MaterialTheme {
        ListTipoPenalidadBody(
            state = ListTipoPenalidadUiState(
                tipos = listOf(
                    TipoPenalidad(1, "Tardanza", "Llegar tarde", 5),
                    TipoPenalidad(2, "Falta", "No asistir", 10)
                )
            ),
            onEvent = {},
            onOpenDrawer = {},
            navigateToEdit = {}
        )
    }
}
