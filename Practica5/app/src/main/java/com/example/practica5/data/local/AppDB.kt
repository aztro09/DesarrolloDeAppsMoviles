package com.example.practica5.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practica5.data.model.Favourite

@Database(entities = [Favourite::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun get(context: Context): AppDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                "tvmaze_db"
            ).build().also { INSTANCE = it }
        }
    }
}