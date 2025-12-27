package com.example.practica5.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practica5.data.model.Favourite

@Dao
interface FavouriteDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourites")
    suspend fun getFavourites(): List<Favourite>

    @Query("DELETE FROM favourites WHERE showId = :id")
    suspend fun deleteFavourites(id: Int)
}

