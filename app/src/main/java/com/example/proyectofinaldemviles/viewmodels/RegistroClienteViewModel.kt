package com.example.proyectofinaldemviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.models.RegistroResponse
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegistroClienteViewModel : ViewModel() {

    private val repository = ChambaRepository()

    private val _registroStatus = MutableLiveData<RegistroResponse?>()
    val registroStatus: LiveData<RegistroResponse?> = _registroStatus

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registrarCliente(nombre: String, apellido: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val request = RegistroRequest(nombre, apellido, email, password)
                val response = repository.registrarCliente(request)
                _registroStatus.postValue(response)
                _error.postValue(null)
            } catch (e: HttpException) {
                _registroStatus.postValue(null)
                when (e.code()) {
                    401 -> _error.postValue("No autorizado. Verifica tus credenciales.")
                    422 -> _error.postValue("Datos inválidos. Revisa la información ingresada.")
                    500 -> _error.postValue("Error del servidor. Intenta más tarde.")
                    else -> _error.postValue("Error: ${e.message()}")
                }
            } catch (e: Exception) {
                _registroStatus.postValue(null)
                _error.postValue("Error en el registro: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
