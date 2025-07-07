package com.example.proyectofinaldemoviles.repository

import com.example.proyectofinaldemviles.api.ChambaApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import android.content.Context
import android.content.SharedPreferences

object RetrofitRepository {
    private const val BASE_URL = "http://trabajos.jmacboy.com/api/"

    // Contexto para acceder a SharedPreferences
    private var appContext: Context? = null
    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val authInterceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()
        val url = originalRequest.url().toString()
        // No agregar Authorization en login ni registro
        if (url.contains("client/login") || url.contains("client/register")) {
            return@Interceptor chain.proceed(originalRequest)
        }
        val prefs: SharedPreferences? = appContext?.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = prefs?.getString("access_token", null)
        val newRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        chain.proceed(newRequest)
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ChambaApi by lazy {
        retrofit.create(ChambaApi::class.java)
    }
}
