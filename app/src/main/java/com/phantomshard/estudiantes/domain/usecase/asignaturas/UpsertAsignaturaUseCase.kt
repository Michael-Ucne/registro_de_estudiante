package com.phantomshard.estudiantes.domain.usecase.asignaturas

import com.phantomshard.estudiantes.domain.model.Asignatura
import com.phantomshard.estudiantes.domain.model.ValidationResult
import com.phantomshard.estudiantes.domain.repository.AsignaturaRepository
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura): Result<Unit> {
        if (validateCodigo(asignatura.codigo).isValid.not()) return Result.failure(Exception("Código inválido"))
        if (validateNombreAsignatura(asignatura.nombre).isValid.not()) return Result.failure(Exception("Nombre inválido"))
        if (validateAula(asignatura.aula).isValid.not()) return Result.failure(Exception("Aula inválida"))
        if (validateCreditos(asignatura.creditos.toString()).isValid.not()) return Result.failure(Exception("Créditos inválidos"))
        
        if (repository.existsByNombre(asignatura.nombre, asignatura.asignaturaId)) {
            return Result.failure(Exception("Ya existe una asignatura registrada con este nombre"))
        }

        repository.upsert(asignatura)
        return Result.success(Unit)
    }
}
