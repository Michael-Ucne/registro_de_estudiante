package com.phantomshard.estudiantes.domain.repository

import com.phantomshard.estudiantes.domain.model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {
    fun observeEstudiantes(): Flow<List<Estudiante>>
    suspend fun getEstudiante(id: Int): Estudiante?
    suspend fun upsert(estudiante: Estudiante): Int
    suspend fun delete(id: Int)
}
