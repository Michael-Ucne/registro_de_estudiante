package com.phantomshard.estudiantes.domain.use_cases.tipopenalidad

import com.phantomshard.estudiantes.domain.repository.TipoPenalidadRepository
import javax.inject.Inject

class GetTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) = repository.getById(id)
}
