package com.phantomshard.estudiantes.data.estudiantes.mapper

import com.phantomshard.estudiantes.data.estudiantes.local.TaskEntity
import com.phantomshard.estudiantes.domain.model.Task

fun TaskEntity.toDomain(): Task = Task(
    estudianteId = estudianteId,
    nombre = nombre,
    email = email,
    edad = edad
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    estudianteId = estudianteId,
    nombre = nombre,
    email = email,
    edad = edad
)