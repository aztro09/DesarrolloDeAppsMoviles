
package com.andres.rastreador.data

import kotlinx.coroutines.flow.Flow

class LocationRepository(private val dao: LocationDao) {
    suspend fun insert(lat: Double, lon: Double, acc: Float, ts: Long) {
        dao.insert(LocationEntity(latitude = lat, longitude = lon, accuracy = acc, timestamp = ts))
    }
    fun getAllFlow(): Flow<List<LocationEntity>> = dao.getAllFlow()
    suspend fun clear() = dao.clear()
}
