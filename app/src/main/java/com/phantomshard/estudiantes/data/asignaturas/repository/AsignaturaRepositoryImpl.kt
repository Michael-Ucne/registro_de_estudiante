package com.phantomshard.estudiantes.data.asignaturas.repository

import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaDao
import com.phantomshard.estudiantes.data.asignaturas.mapper.toDomain
import com.phantomshard.estudiantes.data.asignaturas.mapper.toEntity
import com.phantomshard.estudiantes.domain.model.Asignatura
import com.phantomshard.estudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {
    override fun observeAsignaturas(): Flow<List<Asignatura>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getAsignatura(id: Int): Asignatura? = dao.getById(id)?.toDomain()

    override suspend fun upsert(asignatura: Asignatura) {
        dao.upsert(asignatura.toEntity())
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun existsByNombre(nombre: String, idActual: Int): Boolean {
        return dao.existsByNombre(nombre, idActual)
    }
}
