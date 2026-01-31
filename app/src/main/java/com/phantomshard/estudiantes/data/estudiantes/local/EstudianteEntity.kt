package com.phantomshard.estudiantes.data.estudiantes.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes")
data class EstudianteEntity(
    @PrimaryKey(autoGenerate = true)
    val estudianteId: Int = 0,
    val nombre: String,
    val email: String,
    val edad: Int
)
