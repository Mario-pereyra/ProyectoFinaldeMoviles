package com.example.proyectofinaldemviles.api

import com.example.proyectofinaldemviles.models.Categoria
import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.LoginResponse
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.models.RegistroResponse
import com.example.proyectofinaldemviles.models.Trabajador
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChambaApi {
    @POST("client/login")
    suspend fun iniciarSesion(@Body request: LoginRequest): LoginResponse

    @POST("client/register")
    suspend fun registrarCliente(@Body request: RegistroRequest): RegistroResponse

    @GET("categories")
    suspend fun getCategorias(): List<Categoria>

    @GET("categories/{id}/workers")
    suspend fun getTrabajadoresPorCategoria(@Path("id") categoryId: Int): List<Trabajador>
}