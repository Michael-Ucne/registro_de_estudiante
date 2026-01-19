package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.model.Task
import com.phantomshard.estudiantes.domain.repository.TaskRepository
import javax.inject.Inject

class UpsertTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Int> {
        val nombreResult = validateNombre(task.nombre)
        if (!nombreResult.isValid) {
            return Result.failure(IllegalArgumentException(nombreResult.error))
        }

        val emailResult = validateEmail(task.email)
        if (!emailResult.isValid) {
            return Result.failure(IllegalArgumentException(emailResult.error))
        }

        val edadResult = validateEdad(task.edad.toString())
        if (!edadResult.isValid) {
            return Result.failure(IllegalArgumentException(edadResult.error))
        }

        return runCatching { repository.upsert(task) }
    }
}