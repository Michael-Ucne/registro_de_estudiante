package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
