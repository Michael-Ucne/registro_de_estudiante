package com.phantomshard.estudiantes.domain.model

class PenalidadValidation {
    fun validate(nombre: String, puntos: String): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (nombre.isBlank()) errors["nombre"] = "El nombre es requerido"
        val p = puntos.toIntOrNull()
        if (p == null || p <= 0) errors["puntos"] = "Debe ser un número mayor a 0"
        return errors
    }
}
