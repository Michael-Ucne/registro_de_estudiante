package com.phantomshard.estudiantes.presentation.asignaturas.list

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phantomshard.estudiantes.domain.model.Asignatura

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAsignaturaScreen(
    onOpenDrawer: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    viewModel: ListAsignaturaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            onNavigateToEdit(0)
            viewModel.onEvent(ListAsignaturaUiEvent.NavigationDone)
        }
    }

    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let { id ->
            onNavigateToEdit(id)
            viewModel.onEvent(ListAsignaturaUiEvent.NavigationDone)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ListAsignaturaUiEvent.CreateNew) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Asignatura")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.asignaturas.isEmpty()) {
                Text(
                    text = "No hay asignaturas registradas",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.asignaturas) { asignatura ->
                        AsignaturaCard(
                            asignatura = asignatura,
                            onEdit = { onNavigateToEdit(asignatura.asignaturaId) },
                            onDelete = { viewModel.onEvent(ListAsignaturaUiEvent.Delete(asignatura.asignaturaId)) }
                        )
                    }
                }
            }
            
            state.message?.let {
                Snackbar(modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)) {
                    Text(it)
                    viewModel.onEvent(ListAsignaturaUiEvent.ClearMessage)
                }
            }
        }
    }
}

@Composable
fun AsignaturaCard(
    asignatura: Asignatura,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEdit() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = asignatura.nombre, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(text = "Código: ${asignatura.codigo}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Aula: ${asignatura.aula} - Créditos: ${asignatura.creditos}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(
                onClick = onEdit,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.secondary)
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
