package com.example.proyectofinaldemviles.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.LoginRequest
import com.example.proyectofinaldemviles.models.LoginResponse
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val repository = ChambaRepository()

    private val _loginStatus = MutableLiveData<LoginResponse?>()
    val loginStatus: LiveData<LoginResponse?> = _loginStatus

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun iniciarSesion(context: Context, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val request = LoginRequest(email, password)
                val response = repository.iniciarSesion(request)
                // Guardar el token en SharedPreferences
                response.accessToken?.let { token ->
                    val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("access_token", token).apply()
                }
                _loginStatus.postValue(response)
                _error.postValue(null)
            } catch (e: HttpException) {
                _loginStatus.postValue(null)
                when (e.code()) {
                    401 -> _error.postValue("Credenciales incorrectas. Inténtalo de nuevo.")
                    422 -> _error.postValue("Datos inválidos. Verifica la información.")
                    500 -> _error.postValue("Error del servidor. Intenta más tarde.")
                    else -> _error.postValue("Error: ${e.message()}")
                }
            } catch (e: Exception) {
                _loginStatus.postValue(null)
                _error.postValue("Error en el inicio de sesión: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
