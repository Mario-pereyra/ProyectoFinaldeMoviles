package com.example.proyectofinaldemviles.models

import com.google.gson.annotations.SerializedName

// Respuesta del Login
data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String
)