package com.phantomshard.estudiantes.data.asignaturas.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignaturas")
data class AsignaturaEntity(
    @PrimaryKey(autoGenerate = true)
    val asignaturaId: Int = 0,
    val codigo: String,
    val nombre: String,
    val aula: String,
    val creditos: Int
)
