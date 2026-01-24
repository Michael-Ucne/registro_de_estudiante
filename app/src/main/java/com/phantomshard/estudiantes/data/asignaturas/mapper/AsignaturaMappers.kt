package com.phantomshard.estudiantes.data.asignaturas.mapper

import com.phantomshard.estudiantes.data.asignaturas.local.AsignaturaEntity
import com.phantomshard.estudiantes.domain.model.Asignatura

fun AsignaturaEntity.toDomain() = Asignatura(
    asignaturaId = asignaturaId,
    codigo = codigo,
    nombre = nombre,
    aula = aula,
    creditos = creditos
)

fun Asignatura.toEntity() = AsignaturaEntity(
    asignaturaId = asignaturaId,
    codigo = codigo,
    nombre = nombre,
    aula = aula,
    creditos = creditos
)
