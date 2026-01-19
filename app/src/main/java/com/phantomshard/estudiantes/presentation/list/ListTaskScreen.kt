package com.phantomshard.estudiantes.presentation.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.phantomshard.estudiantes.domain.model.Task

@Composable
fun ListTaskScreen(
    navigateToEditTask: (taskId: Int) -> Unit,
    viewModel: ListTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            navigateToEditTask(0)
        }
    }
    
    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let { id ->
            navigateToEditTask(id)
        }
    }

    ListTaskBody(
        state = state,
        onEvent = viewModel::onEvent,
        navigateToEditTask = navigateToEditTask
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTaskBody(
    state: ListTaskUiState,
    onEvent: (ListTaskUiEvent) -> Unit,
    navigateToEditTask: (taskId: Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Estudiantes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListTaskUiEvent.CreateNew) },
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
                Log.d("ListTaskScreen", "No hay estudiantes registrados")
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
                        .testTag("task_list")
                ) {
                    items(state.tasks) { task ->
                        TaskCard(
                            task = task,
                            onClick = { onEvent(ListTaskUiEvent.Edit(task.estudianteId)) },
                            onEdit = { onEvent(ListTaskUiEvent.Edit(task.estudianteId)) },
                            onDelete = { onEvent(ListTaskUiEvent.Delete(task.estudianteId)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onClick: (Task) -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("task_card_${task.estudianteId}"),
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
                    .clickable { onClick(task) }
            ) {
                Text(task.nombre, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text("Email: ${task.email}", style = MaterialTheme.typography.bodyMedium)
                Text("Edad: ${task.edad}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(
                onClick = { onEdit(task.estudianteId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("edit_button_${task.estudianteId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar estudiante",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = { onDelete(task.estudianteId) },
                modifier = Modifier
                    .size(40.dp)
                    .testTag("delete_button_${task.estudianteId}")
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
private fun ListTaskBodyPreview() {
    val state = ListTaskUiState()
    MaterialTheme {
        ListTaskBody(
            state = state,
            onEvent = {},
            navigateToEditTask = { _ -> }
        )
    }
}
