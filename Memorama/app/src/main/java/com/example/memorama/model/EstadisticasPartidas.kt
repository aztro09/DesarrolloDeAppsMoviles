import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estadisticas")
data class EstadisticasPartidas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

)