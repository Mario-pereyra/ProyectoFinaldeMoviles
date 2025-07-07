package com.example.proyectofinaldemviles.models

import com.google.gson.annotations.SerializedName

data class RegistroRequest(
    val name: String,
    @SerializedName("lastName")
    val lastName: String,
    val email: String,
    val password: String
)
