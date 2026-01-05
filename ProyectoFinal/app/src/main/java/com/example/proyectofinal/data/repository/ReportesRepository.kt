package com.example.proyectofinal.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.utils.await
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await

class ReportesRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
){
    suspend fun subirReporte(uri: Uri, reporte: Reporte): String {
        val ref = storage.reference.child("reportes/$reporteId.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }

    suspend fun crearReporte(reporte: Reporte): String{
        val doc = firestore.collection("reportes").document()
        val payload = r.copy(id = doc.id)
        doc.set(payload).await()
        return doc.id
    }

    fun escucharReportes(onChange: (List<Reporte>) -> Unit): ListenerRegistration {
        firestore.collection("reportes")
            .addSnapshotListener{snap, e ->
                if(e != null || snap == null) return@addSnapshotListener
                onChange(snap.toObjects(Reporte::class.java))
            }
    }
}
