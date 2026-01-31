package com.phantomshard.estudiantes.domain.repository

import com.phantomshard.estudiantes.domain.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    suspend fun save(tipo: TipoPenalidad)
    suspend fun delete(id: Int)
    suspend fun getById(id: Int): TipoPenalidad?
    fun getAll(): Flow<List<TipoPenalidad>>
}
