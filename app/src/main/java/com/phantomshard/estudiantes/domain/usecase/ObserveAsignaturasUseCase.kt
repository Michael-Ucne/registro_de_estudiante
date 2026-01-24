package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.model.Asignatura
import com.phantomshard.estudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAsignaturasUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> = repository.observeAsignaturas()
}
