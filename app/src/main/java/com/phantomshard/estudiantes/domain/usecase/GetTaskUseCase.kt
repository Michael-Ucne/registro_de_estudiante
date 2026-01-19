package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.model.Task
import com.phantomshard.estudiantes.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task? = repository.getTask(id)
}