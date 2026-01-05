package com.example.proyectofinal.data.model

data class Reporte(
    val id: String = "",
    val categoria: Categoria = Categoria.GENERAL,
    val subtipo: String? = null,
    val descripcion: String = "",
    val alias: String? = null,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val fotoPath: String? = null,
    val fecha: Long = System.currentTimeMillis(),
    val alcaldiaId: String? = null,
    val estado: String = "NUEVO"

)
