import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.memorama.ui.CartaItem

@Composable
fun Tablero(cartas: List<Carta>, onCartaClick: (Int) -> Unit) {
    val columnas = 4
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnas),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(cartas){ carta ->
            CartaItem(carta = carta, onClick = { onCartaClick(carta.id) })
        }
    }
}