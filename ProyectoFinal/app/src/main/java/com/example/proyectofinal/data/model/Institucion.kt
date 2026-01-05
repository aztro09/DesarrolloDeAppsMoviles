package com.example.proyectofinal.data.model

data class Institucion(
    val id: String = "",
    val nombre: String = "",
    val categoria: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val horario: String = "",
    val website: String = "",
    val lat: Double? = null,
    val lng: Double? = null
)