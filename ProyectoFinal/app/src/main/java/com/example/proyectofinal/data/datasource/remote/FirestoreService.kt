package com.example.proyectofinal.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreService{
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
}