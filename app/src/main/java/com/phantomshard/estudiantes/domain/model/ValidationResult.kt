package com.phantomshard.estudiantes.domain.model

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)
