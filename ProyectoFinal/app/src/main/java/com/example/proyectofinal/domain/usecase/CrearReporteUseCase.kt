package com.example.proyectofinal.domain.usecase

import android.net.Uri
import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.data.repository.ReportesRepository

class CrearReporteUseCase(private val repo: ReportesRepository){
    suspend operator fun invoke(reporte: Reporte, evidenciaUri: Uri?): String{
        val idTmp = System.currentTimeMillis().toString()
        val fotoUrl = evidenciaUri?.let{repo.subirFoto(it, idTmp)}

        return repo.crearReporte(reporte.copy(id = idTmp, fotoPath = fotoUrl))
    }
}