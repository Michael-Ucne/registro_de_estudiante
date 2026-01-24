package com.phantomshard.estudiantes.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditTaskScreen(
    taskId: Int,
    goBack: (() -> Unit)? = null,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) goBack?.invoke()
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) goBack?.invoke()
    }

    EditTaskBody(
        state = state,
        onEvent = viewModel::onEvent,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTaskBody(
    state: EditTaskUiState,
    onEvent: (EditTaskUiEvent) -> Unit,
    goBack: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nuevo Estudiante" else "Editar Estudiante") },
                navigationIcon = {
                    if (goBack != null) {
                        IconButton(onClick = goBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditTaskUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_nombre")
            )
            if (state.nombreError != null) {
                Text(
                    text = state.nombreError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(EditTaskUiEvent.EmailChanged(it)) },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.emailError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_email")
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.edad,
                onValueChange = { onEvent(EditTaskUiEvent.EdadChanged(it)) },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.edadError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_edad")
            )
            if (state.edadError != null) {
                Text(
                    text = state.edadError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { if (!state.isSaving) onEvent(EditTaskUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_guardar")
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Guardar")
                    }
                }

                if (!state.isNew) {
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = { onEvent(EditTaskUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_eliminar")
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskBodyPreview() {
    val state = EditTaskUiState()
    MaterialTheme {
        EditTaskBody(
            state = state,
            onEvent = {}
        )
    }
}
