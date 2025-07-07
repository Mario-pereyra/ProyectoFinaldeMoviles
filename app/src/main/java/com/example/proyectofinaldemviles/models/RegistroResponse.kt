package com.example.proyectofinaldemviles.models

// Respuesta del Registro
data class RegistroResponse(
    val id: Int,
    val name: String,
    val email: String,
    val profile: Profile
)

data class Profile(
    val id: Int,
    val name: String,
    val last_name: String,
    val type: Int
)