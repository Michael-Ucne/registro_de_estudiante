package com.phantomshard.estudiantes.domain.use_cases.tipopenalidad

import com.phantomshard.estudiantes.domain.repository.TipoPenalidadRepository
import javax.inject.Inject

class ObserveTiposPenalidadesUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke() = repository.getAll()
}
