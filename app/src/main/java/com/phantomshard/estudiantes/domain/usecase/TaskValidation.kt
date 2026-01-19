package com.phantomshard.estudiantes.domain.usecase

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombre(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "El nombre es requerido")
    if (value.length < 3) return ValidationResult(false, "Mínimo 3 caracteres")
    return ValidationResult(true)
}

fun validateEmail(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "El email es requerido")
    return ValidationResult(true)
}

fun validateEdad(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "La edad es requerida")
    val number = value.toIntOrNull() ?: return ValidationResult(false, "Debe ser número entero")
    if (number <= 0) return ValidationResult(false, "Debe ser mayor que 0")
    return ValidationResult(true)
}
