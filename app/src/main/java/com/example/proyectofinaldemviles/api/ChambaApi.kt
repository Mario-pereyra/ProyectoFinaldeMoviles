package com.example.proyectofinaldemviles.api

import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.LoginResponse
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.models.RegistroResponse // Importamos el nuevo modelo
import retrofit2.http.Body
import retrofit2.http.POST

interface ChambaApi {

    @POST("client/login")
    suspend fun iniciarSesion(@Body request: LoginRequest): LoginResponse

    @POST("client/register")
    // Cambiamos el tipo de retorno a RegistroResponse
    suspend fun registrarCliente(@Body request: RegistroRequest): RegistroResponse
}