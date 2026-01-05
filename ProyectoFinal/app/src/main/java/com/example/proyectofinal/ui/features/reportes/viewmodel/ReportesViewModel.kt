package com.example.proyectofinal.ui.features.reportes.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.domain.usecase.CrearReporteUseCase
import com.example.proyectofinal.domain.usecase.ObtenerReporteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportesViewModel(
    private val crearReporteUseCase: CrearReporteUseCase,
    private val obtenerReporteUseCase: ObtenerReporteUseCase
) : ViewModel(){

    private val _reportes = MutableStateFlow<List<Reporte>>(emptyList())
    val reportes: StateFlow<List<Reporte>> = _reportes

    private var reg: ListenerRegistration? = null

    fun observarReportes(){
        reg?.remove()
        reg = obtenerReporteUseCase.observar { _reportes.value = it }
    }

    fun detenerObservacion() { reg?.remove(); reg = null }

    fun enviarReporte(base: Reporte, evidenciaUri: Uri?, onSuccess: (String) -> Unit, onError: (Throwable) -> Unit){
        viewModelScope.launch{
            try{
                val id = crearReporteUseCase(base, evidenciaUri)
                onSuccess(id)
            }catch (e: Throwable){
                onError(e)
            }
        }
    }
}
