import androidx.room.*

@Entity(tableName = "estadisticas")
//PROPIEDADES ESTADISTICA
data class EstadisticasPartidas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val fecha: String,
        val ganador: String,
        val puntosGanador: Int,
        val puntosPerdedor: Int,
        val aciertosGanador: Int,
        val erroresGanador: Int,
        val aciertosPerdedor: Int,
        val erroresPerdedor: Int
)