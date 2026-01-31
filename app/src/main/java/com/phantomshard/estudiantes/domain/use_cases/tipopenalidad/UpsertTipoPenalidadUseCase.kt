package com.phantomshard.estudiantes.domain.use_cases.tipopenalidad

import com.phantomshard.estudiantes.domain.model.TipoPenalidad
import com.phantomshard.estudiantes.domain.repository.TipoPenalidadRepository
import javax.inject.Inject

class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipo: TipoPenalidad) = repository.save(tipo)
}
