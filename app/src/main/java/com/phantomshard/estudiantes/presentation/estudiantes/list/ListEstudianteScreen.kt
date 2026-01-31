package com.phantomshard.estudiantes.presentation.estudiantes.list

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phantomshard.estudiantes.domain.model.Estudiante

@Composable
fun ListEstudianteScreen(
    onOpenDrawer: () -> Unit = {},
    navigateToEditEstudiante: (estudianteId: Int) -> Unit,
    viewModel: ListEstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            navigateToEditEstudiante(0)
            viewModel.onEvent(ListEstudianteUiEvent.NavigationDone)
        }
    }
    
    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let { id ->
            navigateToEditEstudiante(id)
            viewModel.onEvent(ListEstudianteUiEvent.NavigationDone)
        }
    }

    ListEstudianteBody(
        state = state,
        onEvent = viewModel::onEvent,
        onOpenDrawer = onOpenDrawer,
        navigateToEditEstudiante = navigateToEditEstudiante
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListEstudianteBody(
    state: ListEstudianteUiState,
    onEvent: (ListEstudianteUiEvent) -> Unit,
    onOpenDrawer: () -> Unit,
    navigateToEditEstudiante: (estudianteId: Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Estudiantes") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListEstudianteUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_add"),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar estudiante"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else if (state.tasks.isEmpty()) {
                Log.d("ListEstudianteScreen", "No hay estudiantes registrados")
                Text(
                    text = "No hay estudiantes registrados",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag("estudiante_list")
                ) {
                    items(state.tasks) { estudiante ->
                        EstudianteCard(
                            estudiante = estudiante,
                            onClick = { onEvent(ListEstudianteUiEvent.Edit(estudiante.estudianteId)) },
                            onEdit = { onEvent(ListEstudianteUiEvent.Edit(estudiante.estudianteId)) },
                            onDelete = { onEvent(ListEstudianteUiEvent.Delete(estudiante.estudianteId)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EstudianteCard(
    estudiante: Estudiante,
    onClick: (Estudiante) -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("estudiante_card_${estudiante.estudianteId}"),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick(estudiante) }
            ) {
                Text(estudiante.nombre, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text("Email: ${estudiante.email}", style = MaterialTheme.typography.bodyMedium)
                Text("Edad: ${estudiante.edad}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(
                onClick = { onEdit(estudiante.estudianteId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("edit_button_${estudiante.estudianteId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar estudiante",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { onDelete(estudiante.estudianteId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("delete_button_${estudiante.estudianteId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar estudiante",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListEstudianteBodyPreview() {
    val state = ListEstudianteUiState()
    MaterialTheme {
        ListEstudianteBody(
            state = state,
            onEvent = {},
            onOpenDrawer = {},
            navigateToEditEstudiante = { _ -> }
        )
    }
}
