package com.example.proyectofinaldemviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.LoginResponse
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = ChambaRepository()

    private val _loginStatus = MutableLiveData<LoginResponse?>()
    val loginStatus: LiveData<LoginResponse?> = _loginStatus

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun iniciarSesion(email: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = repository.iniciarSesion(request)
                _loginStatus.postValue(response)
                _error.postValue(null) // Limpiar errores previos
            } catch (e: Exception) {
                _loginStatus.postValue(null) // Indicar que el login falló
                _error.postValue("Error en el inicio de sesión: ${e.message}")
            }
        }
    }
}
