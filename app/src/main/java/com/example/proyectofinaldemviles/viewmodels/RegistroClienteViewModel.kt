package com.example.proyectofinaldemviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.models.RegistroResponse
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch

class RegistroClienteViewModel : ViewModel() {

    private val repository = ChambaRepository()

    private val _registroStatus = MutableLiveData<RegistroResponse?>()
    val registroStatus: LiveData<RegistroResponse?> = _registroStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun registrarCliente(nombre: String, apellido: String, email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                // Aquí se espera un RegistroResponse del repositorio
                val request = RegistroRequest(nombre, apellido, email, contrasena)
                val response = repository.registrarCliente(request)
                _registroStatus.postValue(response)
            } catch (e: Exception) {
                // El error se maneja y se postea un valor nulo al LiveData de éxito
                _registroStatus.postValue(null)
                _error.postValue("Error en el registro: ${e.message}")
            }
        }
    }
}