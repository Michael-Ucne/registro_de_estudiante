package com.phantomshard.estudiantes.domain.usecase.estudiantes

import com.phantomshard.estudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}