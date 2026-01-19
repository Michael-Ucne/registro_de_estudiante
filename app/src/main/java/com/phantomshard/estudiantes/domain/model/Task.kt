package com.phantomshard.estudiantes.domain.model

data class Task(
    val estudianteId: Int = 0,
    val nombre: String,
    val email: String,
    val edad: Int
)
