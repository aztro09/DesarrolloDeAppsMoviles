
package com.andres.rastreador.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andres.rastreador.data.LocationEntity
import com.andres.rastreador.ui.HistoryViewModel
import com.andres.rastreador.ui.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(vm: MainViewModel, hvm: HistoryViewModel, onBack: () -> Unit) {
    val locations by vm.locations.collectAsState()
    val sdf = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Historial de ubicaciones") },
            navigationIcon = { TextButton(onClick = onBack) { Text("← Atrás") } },
            actions = {
                TextButton(onClick = { hvm.clear() }) { Text("Limpiar historial") }
            }
        )
    }) { padding ->
        LazyColumn(Modifier.padding(padding).padding(horizontal = 8.dp)) {
            items(locations) { row ->
                LocationRow(row, sdf)
                Divider()
            }
        }
    }
}

@Composable
private fun LocationRow(e: LocationEntity, sdf: SimpleDateFormat) {
    val date = sdf.format(Date(e.timestamp))
    androidx.compose.foundation.layout.Column(Modifier.padding(12.dp)) {
        Text("Fecha/hora: $date")
        Text("Lat: %.6f | Lon: %.6f".format(e.latitude, e.longitude))
        Text("Precisión: ±%d m".format(e.accuracy.toInt()))
    }
}
