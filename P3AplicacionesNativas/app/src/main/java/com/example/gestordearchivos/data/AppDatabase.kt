package com.example.gestordearchivos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentFileEntity::class, FavouriteFileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun fileDao(): FileDAO

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "file_database"
                ).build().also() { INSTANCE = it }
            }
        }
    }
}