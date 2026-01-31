package com.phantomshard.estudiantes.domain.usecase.estudiantes

import com.phantomshard.estudiantes.domain.model.Estudiante
import com.phantomshard.estudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? = repository.getEstudiante(id)
}