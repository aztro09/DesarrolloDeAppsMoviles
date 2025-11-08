package com.example.memorama.viewmodel

import Carta
import EstadisticasPartidas
import EstadoJuego
import Jugador
import AppDatabase
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import androidx.room.util.copy
import androidx.room.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
class MemoramaViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private  val _estadoJuego = MutableStateFlow(savedStateHandle.get<EstadoJuego>("estadoJuego") ?: inicializarEstado())
    val estadoJuego: StateFlow<EstadoJuego> = _estadoJuego.asStateFlow()

    //GUARDADO DE ESTADO DE PARTIDA
    init {
        viewModelScope.launch{
            _estadoJuego.collect{
                savedStateHandle["estadoJuego"] = it
            }
        }
    }
    fun seleccionarCarta(cartaId: Int, context: Context){
        val estado = _estadoJuego.value
        val carta = estado.tablero.find {it.id == cartaId} ?: return

        if (carta.estaVolteada || carta.estaEmparejada || estado.cartasSeleccionadas.size == 2) return

        val nuevaCarta = carta.copy(estaVolteada = true)
        val nuevoTablero = estado.tablero.map {if (it.id == cartaId) nuevaCarta else it}

        val nuevasSeleccionadas = estado.cartasSeleccionadas + nuevaCarta

        _estadoJuego.value = estado.copy(
            tablero = nuevoTablero,
            cartasSeleccionadas = nuevasSeleccionadas
        )

        if (nuevasSeleccionadas.size == 2){
            verificarEmparejamiento(nuevasSeleccionadas, context)
        }
    }

    private fun verificarEmparejamiento(cartas: List<Carta>, context: Context){
        val estado = _estadoJuego.value
        val (c1, c2) = cartas
        val emparejadas = c1.valor == c2.valor

        val nuevoTablero = estado.tablero.map{
            when(it.id){
                c1.id, c2.id -> it.copy(
                    estaEmparejada = emparejadas,
                    estaVolteada = emparejadas
                )
                else -> it
            }
        }

        val jugadorActual = estado.jugadorActual
        val nuevoJugadorActual = if(emparejadas){
            jugadorActual.copy(puntos = jugadorActual.puntos + 1)
        }else{
            jugadorActual
        }

        val siguienteJugador = if (emparejadas) jugadorActual else {
            if (jugadorActual.nombre == estado.jugador1.nombre) estado.jugador2 else estado.jugador1
        }

        val juegoFinalizado = nuevoTablero.all {it.estaEmparejada}
        val ganador = if (juegoFinalizado){
            when{
                estado.jugador1.puntos > estado.jugador2.puntos -> estado.jugador1.nombre
                estado.jugador2.puntos > estado.jugador1.puntos -> estado.jugador2.nombre
                else -> "Empate"
            }
        }else null

        _estadoJuego.value = estado.copy(
            tablero = nuevoTablero,
            jugador1 = if (estado.jugador1.nombre == jugadorActual.nombre)  nuevoJugadorActual else estado.jugador1,
            jugador2 = if (estado.jugador2.nombre == jugadorActual.nombre)  nuevoJugadorActual else estado.jugador2,
            jugadorActual = siguienteJugador,
            cartasSeleccionadas = emptyList(),
            juegoFinalizado = juegoFinalizado,
            ganador = ganador
        )

        //GUARDADO AUTOMATICO DE PARTIDA
        if (juegoFinalizado){ guardarEstadisticas(context)}
    }

    fun verificarFinDeJuego(context: Context){
        val estado = estadoJuego.value
        val juegoFinalizado = estado.tablero.all { it.estaEmparejada }

        if(juegoFinalizado){
            val ganador = when{
                estado.jugador1.puntos > estado.jugador2.puntos -> estado.jugador1.nombre
                estado.jugador2.puntos > estado.jugador1.puntos -> estado.jugador2.nombre
                else -> "Empate"
            }
            _estadoJuego.value = estado.copy(
                juegoFinalizado = true,
                ganador = ganador
            )

            guardarEstadisticas(context)
        }
    }

    fun reiniciarJuego(){
        _estadoJuego.value = inicializarEstado()
    }

    private fun inicializarEstado(): EstadoJuego{
        val cartas = generarCartas()
        val jugador1 = Jugador("Jugador 1")
        val jugador2 = Jugador("Jugador 2")
        return EstadoJuego(
            tablero = cartas,
            jugadorActual = jugador1,
            jugador1 = jugador1,
            jugador2 = jugador2,
            cartasSeleccionadas = emptyList()
        )
    }

    private fun generarCartas(): List<Carta>{
        val valores = (1..8).toList() //8 pares de cartas
        val cartas = (valores + valores).shuffled().mapIndexed { index, valor ->
            Carta(id = index, valor = valor)
        }
        return cartas
    }

    //GUARDADO DE ESTADO EN .JSON
     fun guardarEstadoEnJson(context: Context){
        try {
            val estado = estadoJuego.value
            val json = JSONObject().apply {
                put("jugador1", JSONObject().apply {
                    put("nombre", estado.jugador1.nombre)
                    put("puntos", estado.jugador1.puntos)
                })
                put("jugador2", JSONObject().apply {
                    put("nombre", estado.jugador2.nombre)
                    put("puntos", estado.jugador2.puntos)
                })
                put("jugadorActual", estado.jugadorActual.nombre)
                put("juegoFinalizado", estado.juegoFinalizado)
                put("tablero", JSONArray().apply {
                    estado.tablero.forEach {
                        put(JSONObject().apply {
                            put("id", it.id)
                            put("valor", it.valor)
                            put("estaVolteada", it.estaVolteada)
                            put("estaEmparejada", it.estaEmparejada)
                        })
                    }
                })
                put("tablero", JSONArray().apply {
                    estado.tablero.forEach {
                        put("id", it.id)
                        put("valor", it.valor)
                        put("estaVolteada", it.estaVolteada)
                        put("estaEmparejada", it.estaEmparejada)
                    }
                })
            }

            val file = File(context.filesDir, "estado_partida.json")
            file.writeText(json.toString(4))

            Log.i("Guardar Estado", "Estado guardado correctamente en ${file.absolutePath}")
        } catch(e: Exception){
            Log.e("Guardar Estado", "Error al guardar estado ${e.message}")
        }
    }

    fun cargarEstadoDesdeJson(context: Context) {
        try {
            val file = File(context.filesDir, "estado_partida.json")
            if(!file.exists()){
                Log.e("Cargar Estado", "No se encontró el archivo de estado")
                return
            }
            val json = JSONObject(file.readText())
            val jugador1 = Jugador(
                nombre = json.getJSONObject("jugador1").getString("nombre"),
                puntos = json.getJSONObject("jugador1").getInt("puntos")
            )
            val jugador2 = Jugador(
                nombre = json.getJSONObject("jugador2").getString("nombre"),
                puntos = json.getJSONObject("jugador2").getInt("puntos")
            )

            val jugadorActualNombre = json.getString("jugadorActual")
            val jugadorActual = if (jugadorActualNombre == jugador1.nombre) jugador1 else jugador2

            val juegoFinalizado = json.getBoolean("juegoFinalizado")
            val ganador = json.optString("ganador", null)

            val cartasSeleccionadas = mutableListOf<Carta>()
            val seleccionadasArray = json.getJSONArray("cartasSeleccionadas")
            for (i in 0 until seleccionadasArray.length()){
                val c = seleccionadasArray.getJSONObject(i)
                cartasSeleccionadas.add(
                    Carta(
                        id = c.getInt("id"),
                        valor = c.getInt("valor"),
                        estaVolteada = c.getBoolean("estaVolteada"),
                        estaEmparejada = c.getBoolean("estaEmparejada")
                    )
                )
            }

            val tablero = mutableListOf<Carta>()
            val tableroArray = json.getJSONArray("tablero")
            for (i in 0 until tableroArray.length()) {
                val c = tableroArray.getJSONObject(i)

                tablero.add(
                    Carta(
                        id = c.getInt("id"),
                        valor = c.getInt("valor"),
                        estaVolteada = c.getBoolean("estaVolteada"),
                        estaEmparejada = c.getBoolean("estaEmparejada")
                    )
                )
            }

            _estadoJuego.value = EstadoJuego(
                jugador1 = jugador1,
                jugador2 = jugador2,
                jugadorActual = jugadorActual,
                juegoFinalizado = juegoFinalizado,
                ganador = if (ganador.isNullOrEmpty()) null else ganador,
                cartasSeleccionadas = cartasSeleccionadas,
                tablero = tablero
            )

            Log.i("Cargar Estado", "Estado restaurado correctamente")
        } catch(e: Exception){
            Log.e("Cargar Estado", "Error al cargar estado ${e.message}")
        }
    }

     fun guardarEstadisticas(context: Context){
        val estado = estadoJuego.value
        val ganador = estado.ganador ?: return

        val estadistica = EstadisticasPartidas(
            fecha = SimpleDateFormat("dd/MM/yyyy  HH:mm:ss", Locale.getDefault()).format(Date()),
            ganador = ganador,
            puntosGanador = maxOf(estado.jugador1.puntos, estado.jugador2.puntos),
            puntosPerdedor = minOf(estado.jugador1.puntos, estado.jugador2.puntos),
            aciertosGanador = maxOf(estado.jugador1.puntos, estado.jugador2.puntos),
            erroresGanador = 0,
            aciertosPerdedor = minOf(estado.jugador1.puntos, estado.jugador2.puntos),
            erroresPerdedor = 0
        )

        viewModelScope.launch{
            try{
                AppDatabase.getDatabase(context).estadisticasDAO().insertar(estadistica)
            } catch(e: Exception){
                Log.e("Estadistica", "Error al guardar las estadísticas ${e.message}", e)
            }
        }
    }
}