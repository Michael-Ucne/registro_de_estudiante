package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.model.Asignatura
import com.phantomshard.estudiantes.domain.repository.AsignaturaRepository
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? = repository.getAsignatura(id)
}
