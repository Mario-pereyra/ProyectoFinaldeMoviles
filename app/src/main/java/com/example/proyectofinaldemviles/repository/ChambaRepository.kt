package com.example.proyectofinaldemviles.repository

import com.example.proyectofinaldemoviles.repository.RetrofitRepository
import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.models.Categoria
import com.example.proyectofinaldemviles.models.Trabajador

class ChambaRepository {

    private val api = RetrofitRepository.api // ahora es ChambaApi

    suspend fun iniciarSesion(request: LoginRequest) = api.iniciarSesion(request)

    suspend fun registrarCliente(request: RegistroRequest) = api.registrarCliente(request)

    suspend fun getCategorias(): List<Categoria> = api.getCategorias()

    suspend fun getTrabajadoresPorCategoria(categoryId: Int): List<Trabajador> = api.getTrabajadoresPorCategoria(categoryId)
}
