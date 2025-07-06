package com.example.proyectofinaldemviles.models

data class RegistroRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)