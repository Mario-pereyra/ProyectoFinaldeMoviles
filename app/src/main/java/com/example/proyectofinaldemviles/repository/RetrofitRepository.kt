package com.example.proyectofinaldemviles.repository

import com.example.proyectofinaldemviles.api.ChambaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {

    // Reemplaza esta URL con la URL base de tu API
    private const val BASE_URL = "http://trabajos.jmacboy.com/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ChambaApi = retrofit.create(ChambaApi::class.java)
}