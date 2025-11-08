import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//DATA ACCESS
@Dao
interface EstadisticasDAO {
    @Insert
    suspend fun insertar(est: EstadisticasPartidas)

    @Query("SELECT * FROM estadisticas")
    suspend fun obtenerTodas(): List<EstadisticasPartidas>
}
