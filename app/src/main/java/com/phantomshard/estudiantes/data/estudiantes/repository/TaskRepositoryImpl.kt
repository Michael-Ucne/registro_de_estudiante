package com.phantomshard.estudiantes.data.estudiantes.repository

import com.phantomshard.estudiantes.data.estudiantes.local.TaskDao
import com.phantomshard.estudiantes.data.estudiantes.mapper.toDomain
import com.phantomshard.estudiantes.data.estudiantes.mapper.toEntity
import com.phantomshard.estudiantes.domain.model.Task
import com.phantomshard.estudiantes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override fun observeTasks(): Flow<List<Task>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getTask(id: Int): Task? = dao.getById(id)?.toDomain()

    override suspend fun upsert(task: Task): Int {
        dao.upsert(task.toEntity())
        return task.estudianteId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
