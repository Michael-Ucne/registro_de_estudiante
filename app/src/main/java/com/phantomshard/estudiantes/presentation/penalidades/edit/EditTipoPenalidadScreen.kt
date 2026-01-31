package com.phantomshard.estudiantes.presentation.penalidades.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditTipoPenalidadScreen(
    tipoId: Int?,
    goBack: () -> Unit,
    viewModel: EditTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) goBack()
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) goBack()
    }

    EditTipoPenalidadBody(
        state = state,
        onEvent = viewModel::onEvent,
        goBack = goBack
    )
}

@Composable
private fun EditTipoPenalidadBody(
    state: EditTipoPenalidadUiState,
    onEvent: (EditTipoPenalidadUiEvent) -> Unit,
    goBack: () -> Unit
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
                    IconButton(onClick = goBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = if (state.isNew) "Nuevo Tipo de Penalidad" else "Editar Tipo de Penalidad",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth().testTag("input_nombre"),
                isError = state.nombreError != null
            )
            state.nombreError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().testTag("input_descripcion"),
                isError = state.descripcionError != null
            )
            state.descripcionError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            OutlinedTextField(
                value = state.puntos,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.PuntosChanged(it)) },
                label = { Text("Puntos de Descuento") },
                modifier = Modifier.fillMaxWidth().testTag("input_puntos"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.puntosError != null
            )
            state.puntosError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onEvent(EditTipoPenalidadUiEvent.Save) },
                    modifier = Modifier.weight(1f).testTag("btn_guardar")
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Guardar")
                }

                if (!state.isNew) {
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = { onEvent(EditTipoPenalidadUiEvent.Delete) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.weight(1f).testTag("btn_eliminar")
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
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
private fun EditTipoPenalidadScreenPreview() {
    MaterialTheme {
        EditTipoPenalidadBody(
            state = EditTipoPenalidadUiState(nombre = "Tardanza", descripcion = "Llegada tarde clase"),
            onEvent = {},
            goBack = {}
        )
    }
}
