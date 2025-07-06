package com.example.proyectofinaldemviles.api

import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.LoginResponse
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.models.GenericResponse // Se puede usar para registro
import retrofit2.http.Body
import retrofit2.http.POST

interface ChambaApi {

    @POST("client/login") // Endpoint corregido [cite: 18]
    suspend fun iniciarSesion(@Body request: LoginRequest): LoginResponse

    @POST("client/register") // Endpoint corregido [cite: 20]
    suspend fun registrarCliente(@Body request: RegistroRequest): GenericResponse

}