package com.phantomshard.estudiantes.data.asignaturas.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {
    @Query("SELECT * FROM asignaturas ORDER BY asignaturaId DESC")
    fun observeAll(): Flow<List<AsignaturaEntity>>

    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun getById(id: Int): AsignaturaEntity?

    @Upsert
    suspend fun upsert(entity: AsignaturaEntity)

    @Delete
    suspend fun delete(entity: AsignaturaEntity)

    @Query("DELETE FROM asignaturas WHERE asignaturaId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) > 0 FROM asignaturas WHERE nombre = :nombre AND asignaturaId != :idActual")
    suspend fun existsByNombre(nombre: String, idActual: Int): Boolean
}
