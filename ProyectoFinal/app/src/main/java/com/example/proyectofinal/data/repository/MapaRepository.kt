package com.example.proyectofinal.data.repository

import com.example.proyectofinal.data.model.Reporte

class MapaRepository{
    fun densidadPorAlcaldia(reportes: List<Reporte>): Map<String, Int> =
        reportes.groupingBy { it.alcaldiaId ?: "DESCONOCIDA"}.eachCount()
}