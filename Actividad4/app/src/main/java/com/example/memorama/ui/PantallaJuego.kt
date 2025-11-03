import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memorama.viewmodel.MemoramaViewModel

@Composable
fun PantallaJuego(viewModel: MemoramaViewModel = viewModel()) {
    val estado by viewModel.estadoJuego.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Turno de: ${estado.jugadorActual.nombre}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Jugador 1: ${estado.jugador1.puntos} pts")
            Text(text = "Jugador 2: ${estado.jugador2.puntos} pts")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Tablero(cartas = estado.tablero, onCartaClick = { cartaId ->
            viewModel.seleccionarCarta(cartaId)
        })
        Spacer(modifier = Modifier.height(16.dp))

        if (estado.juegoFinalizado) {
            Text(
                text = "Ganador: ${estado.ganador}",
                style = MaterialTheme.typography.titleMedium
            )
            Button(onClick = { viewModel.reiniciarJuego() }) {
                Text(text = "Reiniciar Juego")
            }
        }
    }
}