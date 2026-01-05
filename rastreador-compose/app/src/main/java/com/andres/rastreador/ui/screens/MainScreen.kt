
package com.andres.rastreador.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andres.rastreador.ui.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(vm: MainViewModel, onOpenHistory: () -> Unit) {
    val locations by vm.locations.collectAsState()
    val interval by vm.intervalMs.collectAsState()
    val tracking by vm.trackingActive.collectAsState()
    val notifDiscreta by vm.notifDiscreta.collectAsState()
    val theme by vm.theme.collectAsState()

    val scope = rememberCoroutineScope()

    val current = locations.firstOrNull()
    val currentLat = current?.latitude
    val currentLon = current?.longitude
    val currentAcc = current?.accuracy ?: 0f

    Column(Modifier.fillMaxSize()) {
        val cameraPositionState = rememberCameraPositionState()
        LaunchedEffect(currentLat, currentLon) {
            if (currentLat != null && currentLon != null) {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(LatLng(currentLat, currentLon), 17f)
                )
            }
        }
        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = true),
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            if (currentLat != null && currentLon != null) {
                Marker(
                    state = MarkerState(position = LatLng(currentLat, currentLon)),
                    title = "Posición actual",
                    snippet = "Precisión: ±${currentAcc.toInt()} m"
                )
            }
            val points = locations.map { LatLng(it.latitude, it.longitude) }
            if (points.size >= 2) {
                Polyline(points = points)
            }
        }

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                Text("Coordenadas actuales:")
                Text(
                    if (currentLat != null && currentLon != null)
                        "Lat: %.6f
Lon: %.6f
Precisión: ±%d m".format(currentLat, currentLon, currentAcc.toInt())
                    else "Sin datos aún…"
                )

                Spacer(Modifier.height(12.dp))

                Text("Intervalo de actualización")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IntervalChip("10s", interval == 10_000L) { scope.launch { vm.setInterval(10_000L) } }
                    Spacer(Modifier.width(8.dp))
                    IntervalChip("60s", interval == 60_000L) { scope.launch { vm.setInterval(60_000L) } }
                    Spacer(Modifier.width(8.dp))
                    IntervalChip("5min", interval == 300_000L) { scope.launch { vm.setInterval(300_000L) } }
                }

                Spacer(Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { vm.startTracking() }, enabled = !tracking) { Text("Iniciar rastreo") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { vm.stopTracking() }, enabled = tracking) { Text("Detener rastreo") }
                }

                Spacer(Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(onClick = onOpenHistory, label = { Text("Ver historial") })
                    Spacer(Modifier.width(8.dp))
                    AssistChip(onClick = {
                        scope.launch { vm.setTheme(if (theme == "GUINDA") "AZUL" else "GUINDA") }
                    }, label = { Text("Tema: ${if (theme == "GUINDA") "Guinda" else "Azul"}") })
                    Spacer(Modifier.width(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Notificación discreta")
                        Spacer(Modifier.width(6.dp))
                        Switch(checked = notifDiscreta, onCheckedChange = { checked ->
                            scope.launch { vm.setNotifDiscreta(checked) }
                        })
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text("Estado de rastreo: " + if (tracking) "Activo ✅" else "Inactivo ⛔",
                    style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun IntervalChip(text: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(text) })
}
