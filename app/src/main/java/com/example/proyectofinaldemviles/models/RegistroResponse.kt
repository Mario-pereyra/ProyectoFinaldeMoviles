package com.example.proyectofinaldemviles.models

import com.google.gson.annotations.SerializedName

// Modelo para la respuesta de registro exitoso
data class RegistroResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: UserRegistered
)

// Modelo para el objeto de usuario anidado en la respuesta
data class UserRegistered(
    val name: String,
    val lastName: String,
    val email: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int
)