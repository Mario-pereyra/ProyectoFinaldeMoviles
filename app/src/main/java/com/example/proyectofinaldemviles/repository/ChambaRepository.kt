package com.example.proyectofinaldemviles.repository

import com.example.proyectofinaldemoviles.repository.RetrofitRepository
import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.RegistroRequest

class ChambaRepository {

    private val api = RetrofitRepository.api

    suspend fun iniciarSesion(request: LoginRequest) = api.iniciarSesion(request)

    suspend fun registrarCliente(request: RegistroRequest) = api.registrarCliente(request)
}
