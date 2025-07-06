package com.example.proyectofinaldemviles.repository

import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.RegistroRequest


class ChambaRepository {

    private val api = RetrofitRepository.api

    // Esta función debe devolver lo mismo que la función en ChambaApi
    suspend fun registrarCliente(request: RegistroRequest) = api.registrarCliente(request)

    // Esta función ya estaba correcta, la dejamos como referencia
    suspend fun iniciarSesion(request: LoginRequest) = api.iniciarSesion(request)
}