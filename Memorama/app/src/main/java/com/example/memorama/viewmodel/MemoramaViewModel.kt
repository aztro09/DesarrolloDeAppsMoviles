package com.example.memorama.viewmodel

import Carta
import EstadoJuego
import Jugador
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class MemoramaViewModel : ViewModel() {
    private  val _estadoJuego = MutableStateFlow(inicializarEstado())
    val estadoJuego: StateFlow<EstadoJuego> = _estadoJuego.asStateFlow()

    //GUARDADO DE ESTADO DE PARTIDA
    init {
        viewModelScope.launch{
            _estadoJuego.collect{
                savedStateHandle["estadoJuego"] = it
            }
        }
    }
    fun seleccionarCarta(cartaId: Int){
        val estado = _estadoJuego.value
        val carta = estado.tablero.fin {it.id == cartaId} ?: return

        if (carta.estaVolteada || carta.estaEmparejada || estado.cartasSeleccionadas.size == 2) return

        val nuevaCarta = carta.copy(estaVolteada = true)
        val nuevoTablero = estado.tablero.map {if (it.id == cartaId) nuevaCarta else it}

        val nuevasSeleccionadas = estado.cartasSeleccionadas + nuevaCarta

        _estadoJuego.value = estado.copy(
            tablero = nuevoTablero,
            cartasSeleccionadas = nuevasSeleccionadas
        )

        if (nuevasSeleccionadas.size == 2){
            verificarEmparejamiento(nuevasSeleccionadas)
        }
    }

    private fun verificarEmparejamiento(cartas: List<Carta>){
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
    fun guardarEstadoEnJson(context: context){
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
            put("tablero", JSONArray().apply{
                estado.tablero.forEach {
                    put(JSONObject().apply {
                        put("id", it.id)
                        put("valor", it.valor)
                        put("estaVolteada", it.estaVolteada)
                        put("estaEmparejada", it.estaEmparejada)
                    })
                }
            })
        }
    }

    val file = File(context.filesDir, "estado_partida.json")
    file.writeText(json.toString(4))
}