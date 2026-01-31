package com.phantomshard.estudiantes.data.estudiantes.mapper

import com.phantomshard.estudiantes.data.estudiantes.local.EstudianteEntity
import com.phantomshard.estudiantes.domain.model.Estudiante

fun EstudianteEntity.toDomain(): Estudiante = Estudiante(
    estudianteId = estudianteId,
    nombre = nombre,
    email = email,
    edad = edad
)

fun Estudiante.toEntity(): EstudianteEntity = EstudianteEntity(
    estudianteId = estudianteId,
    nombre = nombre,
    email = email,
    edad = edad
)