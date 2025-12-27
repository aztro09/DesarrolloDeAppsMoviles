package com.example.practica5.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Favourite(
    @PrimaryKey val showId: Int,
    val name: String?,
    val imageUrl: String?
)