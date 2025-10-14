package com.example.gestordearchivos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FileDAO {
    //Recientes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecent(file: RecentFileEntity)

    @Query("SELECT * FROM recent_files ORDER BY lastOpened DESC LIMIT 20")
    suspend fun getRecentFiles(): List<RecentFileEntity>

    //Favs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(file: FavouriteFileEntity)

    @Query("DELETE FROM favourite_files WHERE uri = :uri")
    suspend fun removeFavourite(uri: String)

    @Query("SELECT * FROM favourite_files ORDER BY location DESC")
    suspend fun getFavourites(): List<FavouriteFileEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_files WHERE uri = :uri)")
    suspend fun isFavourite(uri: String): Boolean
}