package com.example.proyectofinaldemviles.models

import com.google.gson.annotations.SerializedName

// Modelo para la respuesta del login que incluye el token [cite: 172]
data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)