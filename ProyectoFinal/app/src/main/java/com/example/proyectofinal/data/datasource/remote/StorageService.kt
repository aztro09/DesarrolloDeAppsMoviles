package com.example.proyectofinal.data.datasource.remote

import com.google.firebase.storage.FirebaseStorage

object StorageService{
    val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
}