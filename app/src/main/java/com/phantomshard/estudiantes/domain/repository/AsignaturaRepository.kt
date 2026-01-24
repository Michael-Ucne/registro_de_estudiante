package com.phantomshard.estudiantes.domain.repository

import com.phantomshard.estudiantes.domain.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    fun observeAsignaturas(): Flow<List<Asignatura>>
    suspend fun getAsignatura(id: Int): Asignatura?
    suspend fun upsert(asignatura: Asignatura)
    suspend fun delete(id: Int)
    suspend fun existsByNombre(nombre: String, idActual: Int): Boolean
}
