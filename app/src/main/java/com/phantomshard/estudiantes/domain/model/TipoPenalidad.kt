package com.phantomshard.estudiantes.domain.model

data class TipoPenalidad(
    val tipoId: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: Int = 0
)
