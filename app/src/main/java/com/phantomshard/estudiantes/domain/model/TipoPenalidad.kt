package com.phantomshard.estudiantes.domain.model

data class TipoPenalidad(
    val tipoId: Int,
    val nombre: String,
    val descripcion: String,
    val puntosDescuento: Int
)
