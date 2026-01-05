package com.example.proyectofinal.ui.features.reportes.screens


import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.data.model.Categoria
import com.example.proyectofinal.data.model.Reporte
import com.example.proyectofinal.ui.features.reportes.viewmodel.ReportesViewModel

@Composable
fun FormCorrupcion(
    vm: ReportesViewModel,
    onEnviado: (String) -> Unit
) {
    var dependencia by remember { mutableStateOf("") }
    var servidorPublico by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf(0.0) }
    var lng by remember { mutableStateOf(0.0) }
    var evidencia by remember {mutableStateOf<Uri?>(null)}

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = dependencia,
            onValueChange = { dependencia = it },
            label = { Text("Dependencia") })
        TextField(
            value = servidorPublico,
            onValueChange = { servidorPublico = it },
            label = { Text("Servidor público (opcional)") })
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") })
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
                categoria = Categoria.CORRUPCION_OMISION,
                subtipo = dependencia.ifBlank { null },
                descripcion = descripcion,
                alias = servidorPublico.ifBlank { null },
                lat = lat, lng = lng
            )
            vm.enviarReporte(r, evidencia, onSuccess = onEnviado, onError = { })
        }){Text("Enviar reporte")}
    }
}
