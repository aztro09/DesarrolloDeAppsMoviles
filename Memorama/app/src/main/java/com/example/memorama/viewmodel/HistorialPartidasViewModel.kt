import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialPartidasViewModel  : ViewModel(){
    private val _partidas = MutableStateFlow<List<EstadisticasPartidas>>(emptyList())
    val partidas: StateFlow<List<EstadisticasPartidas>> = _partidas

    fun cargarPartidas(context: Context){
        viewModelScope.launch {
            val dao = AppDatabase.getDatabase(context).estadisticasDAO()
            _partidas.value = dao.obtenerTodas()
        }
    }
}