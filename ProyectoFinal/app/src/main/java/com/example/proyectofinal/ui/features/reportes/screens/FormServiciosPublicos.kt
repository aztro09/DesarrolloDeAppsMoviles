package com.example.proyectofinal.ui.features.reportes.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.data.model.Categoria
import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.ui.features.reportes.viewmodel.ReportesViewModel

@Composable
fun FormServiciosPublicos(
    vm: ReportesViewModel,
    ubicacionLat: Double? = null,
    ubicacionLng: Double? = null,
    onEnviado: (String) -> Unit
){
    var subtipo by remember {mutableStateOf("bache")}
    var descripcion by remember {mutableStateOf("")}
    var alias by remember {mutableStateOf("")}
    var lat by remember {mutableStateOf(ubicacionLat ?: 0.0)}
    var lng by remember {mutableStateOf(ubicacionLng ?: 0.0)}
    var evidencia by remember {mutableStateOf<Uri?>(null)}

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(value = subtipo, onValueChange = { subtipo = it }, label = { Text("Subtipo") })
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") })
        TextField(
            value = alias,
            onValueChange = { alias = it },
            label = { Text("Alias (opcional)") })
        Row {
            TextField(
                value = lat.toString(),
                onValueChange = { lat = it.toDoubleOrNull() ?: lat },
                label = { Text("Lat") })
            Spacer(Modifier.width(8.dp))
            TextField(
                value = lng.toString(),
                onValueChange = { lng = it.toDoubleOrNull() ?: lng },
                label = { Text("Lng") })
        }

        Button(onClick = {
            val r = Reporte(
                categoria = Categoria.SERVICIOS_PUBLICOS,
                subtipo = subtipo,
                descripcion = descripcion,
                alias = alias,
                lat = lat, lng = lng
            )
            vm.enviarReporte(r, evidencia, onSuccess = onEnviado, onError = {})
        }) {
            Text("Enviar reporte")
        }
    }
}