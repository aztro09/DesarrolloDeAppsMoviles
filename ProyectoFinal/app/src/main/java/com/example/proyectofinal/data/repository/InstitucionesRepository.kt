package com.example.proyectofinal.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.example.proyectofinal.data.model.Institucion
import com.example.proyectofinal.utils.await

class InstitucionesRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    suspend fun cargarInstituciones(): List<Institucion>{
    val snap = firestore.collection("instituciones").get().await()
    return snap.toObjects(Institucion::class.java)
    }
}