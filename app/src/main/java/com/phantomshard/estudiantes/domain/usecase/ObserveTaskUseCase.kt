package com.phantomshard.estudiantes.domain.usecase

import com.phantomshard.estudiantes.domain.model.Task
import com.phantomshard.estudiantes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> = repository.observeTasks()
}