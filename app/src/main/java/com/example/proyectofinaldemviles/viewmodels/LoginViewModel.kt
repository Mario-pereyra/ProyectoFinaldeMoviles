package com.example.proyectofinaldemviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.GenericResponse
import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = ChambaRepository()

    private val _loginStatus = MutableLiveData<GenericResponse?>()
    val loginStatus: LiveData<GenericResponse?> = _loginStatus

    fun iniciarSesion(email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, contrasena)
                val response = repository.iniciarSesion(request)
                _loginStatus.postValue(response)
            } catch (e: Exception) {
                // Manejo de errores (ej. sin conexión)
                _loginStatus.postValue(GenericResponse(false, "Error: ${e.message}"))
            }
        }
    }
}