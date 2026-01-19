package com.phantomshard.estudiantes.domain.repository

import com.phantomshard.estudiantes.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun getTask(id: Int): Task?
    suspend fun upsert(task: Task): Int
    suspend fun delete(id: Int)
}
