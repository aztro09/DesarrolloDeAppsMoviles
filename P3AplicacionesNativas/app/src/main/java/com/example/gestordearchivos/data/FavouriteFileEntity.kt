package com.example.gestordearchivos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_files")
data class FavouriteFileEntity(
    @PrimaryKey val uri: String,
    val name: String,
    val location: Long,
)