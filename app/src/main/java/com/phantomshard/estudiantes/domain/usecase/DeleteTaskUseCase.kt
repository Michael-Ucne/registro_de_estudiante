package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}