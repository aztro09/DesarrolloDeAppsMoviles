package com.example.practica5.data.model

import android.media.Image

data class Show(
    val id: Int,
    val name: String,
    val image: Image?,
    val summary: String?
    )