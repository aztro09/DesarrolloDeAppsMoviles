
package com.andres.rastreador.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert
    suspend fun insert(entity: LocationEntity)

    @Query("SELECT * FROM locations ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<LocationEntity>>

    @Query("DELETE FROM locations")
    suspend fun clear()
}
