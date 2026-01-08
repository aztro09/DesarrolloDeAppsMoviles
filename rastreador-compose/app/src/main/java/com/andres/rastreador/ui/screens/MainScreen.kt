
package com.andres.rastreador.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.andres.rastreador.ui.MainViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
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
        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { ctx ->
                MapView(ctx).apply {
                    setMultiTouchControls(true)
                    setTileSource(TileSourceFactory.MAPNIK)
                    controller.setZoom(17.0)
                }
            },
            update = { mapView ->
                mapView.overlays.clear()
                if (currentLat != null && currentLon != null) {
                    val geo = GeoPoint(currentLat, currentLon)
                    mapView.controller.setCenter(geo)
                    val marker = Marker(mapView)
                    marker.position = geo
                    marker.title = "Posición actual"
                    marker.subDescription = "Precisión: ±${currentAcc.toInt()} m"
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mapView.overlays.add(marker)
                }
                if (locations.size >= 2) {
                    val polyline = Polyline().apply {
                        setPoints(locations.map { GeoPoint(it.latitude, it.longitude) })
                    }
                    mapView.overlays.add(polyline)
                }
                mapView.invalidate()
            }
        )

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
