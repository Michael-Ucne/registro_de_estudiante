package com.phantomshard.estudiantes.data.penalidades.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {
    @Upsert
    suspend fun save(tipo: TipoPenalidadEntity)

    @Delete
    suspend fun delete(tipo: TipoPenalidadEntity)

    @Query("SELECT * FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun getById(id: Int): TipoPenalidadEntity?

    @Query("SELECT * FROM TiposPenalidades")
    fun getAll(): Flow<List<TipoPenalidadEntity>>
}
