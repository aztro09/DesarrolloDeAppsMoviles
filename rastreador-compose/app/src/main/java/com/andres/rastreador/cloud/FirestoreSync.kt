
package com.andres.rastreador.cloud

import android.content.Context
import android.provider.Settings
import com.andres.rastreador.data.LocationEntity
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreSync(context: Context) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val deviceId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    fun uploadLocation(loc: LocationEntity) {
        val data = hashMapOf(
            "latitude" to loc.latitude,
            "longitude" to loc.longitude,
            "accuracy" to loc.accuracy,
            "timestamp" to loc.timestamp,
            "deviceId" to deviceId
        )
        db.collection("locations").add(data)
    }
}
