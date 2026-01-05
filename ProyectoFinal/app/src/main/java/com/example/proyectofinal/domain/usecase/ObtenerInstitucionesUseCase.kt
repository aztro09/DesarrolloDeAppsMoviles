package com.example.proyectofinal.domain.usecase


import com.example.proyectofinal.data.model.Institucion
import com.example.proyectofinal.data.repository.InstitucionesRepository

class ObtenerInstitucionesUseCase(private val repo: InstitucionesRepository){
    suspend operator fun invoke(): List<Institucion> = repo.cargarInstituciones()
}