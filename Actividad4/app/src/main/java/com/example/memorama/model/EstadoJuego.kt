data class Carta(
    val id: Int,
    val valor: Int,
    val estaVolteada: Boolean = false,
    val estaEmparejada: Boolean = false
)

data class Jugador(
    val nombre: String,
    val puntos: Int = 0

)

data class EstadoJuego (
    val tablero: List<Carta>,
    val jugadorActual: Jugador,
    val jugador1: Jugador,
    val jugador2: Jugador,
    val cartasSeleccionadas: List<Carta>,
    val juegoFinalizado: Boolean = false,
    val ganador: String? = null
)
