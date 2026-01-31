package com.phantomshard.estudiantes.domain.usecase.estudiantes

import com.phantomshard.estudiantes.domain.model.Estudiante
import com.phantomshard.estudiantes.domain.model.ValidationResult
import com.phantomshard.estudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante): Result<Int> {
        val nombreResult = validateNombre(estudiante.nombre)
        if (!nombreResult.isValid) {
            return Result.failure(IllegalArgumentException(nombreResult.error))
        }

        val emailResult = validateEmail(estudiante.email)
        if (!emailResult.isValid) {
            return Result.failure(IllegalArgumentException(emailResult.error))
        }

        val edadResult = validateEdad(estudiante.edad.toString())
        if (!edadResult.isValid) {
            return Result.failure(IllegalArgumentException(edadResult.error))
        }

        return runCatching { repository.upsert(estudiante) }
    }
}