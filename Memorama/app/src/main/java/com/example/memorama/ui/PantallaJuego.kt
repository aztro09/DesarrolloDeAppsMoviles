import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memorama.theme.ThemeManager
import com.example.memorama.viewmodel.MemoramaViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaJuego(navController: NavController, viewModel: MemoramaViewModel = viewModel()) {
    val estado by viewModel.estadoJuego.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
            viewModel.seleccionarCarta(cartaId,context)
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

        Button(onClick = {viewModel.guardarEstadoEnJson(context)}){
            Text("Guardar Estado de Partida")
        }

        Button(onClick = {viewModel.cargarEstadoDesdeJson(context)}) {
            Text("Cargar Partida Guardada")
        }

        Text("Seleccione un tema: ", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
            Button(onClick = {scope.launch { ThemeManager.guardarTema(context, "guinda")} }){
                Text("Guinda")
            }
            Button(onClick = {scope.launch { ThemeManager.guardarTema(context, "azul") } }){
                Text("Azul")
            }
            Button(onClick = {scope.launch { ThemeManager.guardarTema(context, "claro")} }){
                Text("Claro")
            }
            Button(onClick = {scope.launch { ThemeManager.guardarTema(context, "oscuro")} }){
                Text("Oscuro")
            }
        }

        Button(onClick =  {
            navController.navigate("configuracion_tema")}) {
            Text("Configurar tema")
        }

        Button(onClick = {navController.navigate("historial")}) {
            Text("Ver historial de partidas")
        }
    }

    Row{
        Button(onClick =  {
          scope.launch { ThemeManager.guardarTema(context, "guinda") }
        }) {
            Text("Tema Guinda")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = {
            scope.launch{ ThemeManager.guardarTema(context, "azul")}
        }){
            Text("Tema Azul")
        }
    }
}