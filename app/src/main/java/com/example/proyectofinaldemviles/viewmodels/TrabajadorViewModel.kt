package com.example.proyectofinaldemviles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.Trabajador
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch

class TrabajadorViewModel(private val repository: ChambaRepository = ChambaRepository()) : ViewModel() {
    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> = _trabajadores

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun cargarTrabajadoresPorCategoria(categoryId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val lista = repository.getTrabajadoresPorCategoria(categoryId)
                // Ordenar por calificación y cantidad de trabajos
                val ordenados = lista.sortedWith(compareByDescending<Trabajador> { it.average_rating }
                    .thenByDescending { it.reviews_count })
                _trabajadores.value = ordenados
            } catch (e: Exception) {
                _trabajadores.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

