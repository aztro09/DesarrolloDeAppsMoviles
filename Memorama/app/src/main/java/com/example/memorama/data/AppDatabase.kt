import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

//BASE DE DATOS
@Database(entities = [EstadisticasPartidas::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun estadisticasDAO(): EstadisticasDAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "memoria_db"
                ).build().also {INSTANCE = it}
            }
        }
    }
}