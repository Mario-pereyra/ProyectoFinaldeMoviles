package com.example.proyectofinaldemviles.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinaldemviles.models.Categoria
import com.example.proyectofinaldemviles.repository.ChambaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CategoriaViewModel : ViewModel() {
    private val repository = ChambaRepository()

    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _filteredCategorias = MutableLiveData<List<Categoria>>()
    val filteredCategorias: LiveData<List<Categoria>> = _filteredCategorias

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarCategorias() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val lista = repository.getCategorias()
                _categorias.postValue(lista)
                _filteredCategorias.postValue(lista)
                _error.postValue(null)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val msg = "HttpException ${e.code()}: ${e.message()}\nBody: $errorBody"
                Log.e("CategoriaViewModel", msg, e)
                _error.postValue(msg)
            } catch (e: Exception) {
                Log.e("CategoriaViewModel", "Exception: ${e.localizedMessage}", e)
                _error.postValue("Exception: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun filtrarCategorias(query: String) {
        val original = _categorias.value ?: return
        if (query.isBlank()) {
            _filteredCategorias.postValue(original)
        } else {
            val filtradas = original.filter { it.name.contains(query, ignoreCase = true) }
            _filteredCategorias.postValue(filtradas)
        }
    }
}
