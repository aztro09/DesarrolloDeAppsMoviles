import EstadisticasPartidas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorama.viewmodel.*


@Composable
fun HistorialPartidasScreen(viewModel: HistorialPartidasViewModel = viewModel()){
    val partidas by viewModel.partidas.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.cargarPartidas(context)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text("Historial de Partidas", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            Card(
            items(partidas){partida ->
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ){
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Fecha: ${partida.fecha}")
                        Text("Ganador: ${partida.ganador}")
                        Text("Puntos: ${partida.puntosGanador} - ${partida.puntosPerdedor}")
                        Text("Aciertos: ${partida.aciertosGanador} - ${partida.aciertosPerdedor}")
                        Text("Errores: ${partida.erroresGanador} - ${partida.erroresPerdedor}")

                    }
                }
            }
        }
    }
}