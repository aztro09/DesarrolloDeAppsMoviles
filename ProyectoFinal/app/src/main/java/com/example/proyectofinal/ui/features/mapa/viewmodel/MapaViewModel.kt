package com.example.proyectofinal.ui.features.mapa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.data.repository.MapaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapaViewModel(private val mapaRepo: MapaRepository) : ViewModel() {
    private val _densidad = MutableStateFlow<Map<String, Int>>(emptyMap())
    val densidad: StateFlow<Map<String, Int>> = _densidad

    fun actualizar(reportes: List<Reporte>){
        viewModelScope.launch {
            _densidad.value = mapaRepo.densidadPorAlcaldia(reportes)
        }
    }
}