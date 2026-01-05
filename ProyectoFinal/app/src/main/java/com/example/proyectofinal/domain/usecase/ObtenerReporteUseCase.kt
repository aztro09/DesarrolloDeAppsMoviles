package com.example.proyectofinal.domain.usecase

import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.data.repository.ReportesRepository
import com.google.firebase.firestore.ListenerRegistration

class ObtenerReporteUseCase(private val repo: ReportesRepository){
    fun observar(onChange: (List<Reporte>) -> Unit): ListenerRegistration = repo.escucharReportes(onChange)
}