package com.phantomshard.estudiantes.domain.use_cases.tipopenalidad

import com.phantomshard.estudiantes.domain.repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
