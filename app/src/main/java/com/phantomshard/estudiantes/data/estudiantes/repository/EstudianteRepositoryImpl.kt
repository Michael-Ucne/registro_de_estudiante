package com.phantomshard.estudiantes.data.estudiantes.repository

import com.phantomshard.estudiantes.data.estudiantes.local.EstudianteDao
import com.phantomshard.estudiantes.data.estudiantes.mapper.toDomain
import com.phantomshard.estudiantes.data.estudiantes.mapper.toEntity
import com.phantomshard.estudiantes.domain.model.Estudiante
import com.phantomshard.estudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EstudianteRepositoryImpl(
    private val dao: EstudianteDao
) : EstudianteRepository {
    override fun observeEstudiantes(): Flow<List<Estudiante>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getEstudiante(id: Int): Estudiante? = dao.getById(id)?.toDomain()

    override suspend fun upsert(estudiante: Estudiante): Int {
        dao.upsert(estudiante.toEntity())
        return estudiante.estudianteId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
