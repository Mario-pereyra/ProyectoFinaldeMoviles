package com.example.proyectofinaldemviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.GenericResponse
import com.example.proyectofinaldemviles.models.RegistroRequest
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch

class RegistroClienteViewModel : ViewModel() {

    private val repository = ChambaRepository()

    private val _registroStatus = MutableLiveData<GenericResponse?>()
    val registroStatus: LiveData<GenericResponse?> = _registroStatus

    fun registrarCliente(nombre: String, apellido: String, email: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val request = RegistroRequest(nombre, apellido, email, contrasena)
                val response = repository.registrarCliente(request)
                _registroStatus.postValue(response)
            } catch (e: Exception) {
                _registroStatus.postValue(GenericResponse(false, "Error: ${e.message}"))
            }
        }
    }
}