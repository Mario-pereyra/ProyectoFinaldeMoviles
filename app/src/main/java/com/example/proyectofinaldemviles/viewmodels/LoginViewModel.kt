package com.example.proyectofinaldemoviles.viewmodels

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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun iniciarSesion(email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, contrasena)
                val response = repository.iniciarSesion(request)
                _loginStatus.postValue(response)
            } catch (e: Exception) {
                _error.postValue("Error en el inicio de sesión: ${e.message}")
            }
        }
    }
}