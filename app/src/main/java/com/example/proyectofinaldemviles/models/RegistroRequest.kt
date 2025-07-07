package com.example.proyectofinaldemviles.models

// Petición para el Registro
data class RegistroRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)