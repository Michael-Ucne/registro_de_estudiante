package com.phantomshard.estudiantes.domain.usecase

fun validateCodigo(codigo: String): ValidationResult {
    return if (codigo.isBlank()) {
        ValidationResult(false, "El código es obligatorio")
    } else {
        ValidationResult(true)
    }
}

fun validateNombreAsignatura(nombre: String): ValidationResult {
    return if (nombre.isBlank()) {
        ValidationResult(false, "El nombre es obligatorio")
    } else {
        ValidationResult(true)
    }
}

fun validateAula(aula: String): ValidationResult {
    return if (aula.isBlank()) {
        ValidationResult(false, "El aula es obligatoria")
    } else {
        ValidationResult(true)
    }
}

fun validateCreditos(creditos: String): ValidationResult {
    val cred = creditos.toIntOrNull()
    return if (cred == null || cred <= 0) {
        ValidationResult(false, "Los créditos deben ser mayores a 0")
    } else {
        ValidationResult(true)
    }
}
