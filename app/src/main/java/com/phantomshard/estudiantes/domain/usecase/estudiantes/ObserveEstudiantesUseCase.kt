package com.phantomshard.estudiantes.domain.usecase.estudiantes

import com.phantomshard.estudiantes.domain.model.Estudiante
import com.phantomshard.estudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudiantesUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> = repository.observeEstudiantes()
}