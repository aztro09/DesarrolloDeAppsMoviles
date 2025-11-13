package com.example.memorama

import HistorialPartidasScreen
import PantallaJuego
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorama.theme.*
import com.example.memorama.ui.ConfiguracionTemaScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val temaFlow = ThemeManager.obtenerTema(this)

        setContent {
            val temaSeleccionado by temaFlow.collectAsState(initial = "guinda")
            val colores = when (temaSeleccionado) {
                "azul" -> temaAzul
                "claro" -> temaClaro
                "oscuro" -> temaOscuro
                else -> temaGuinda //default
            }

            //material theme para envolver todo la app
            MaterialTheme(colorScheme = colores) {
                val navController = rememberNavController()

                Scaffold { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "juego",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        // Definición de las pantallas existentes
                        composable("pantalla_juego") {
                            PantallaJuego(navController = navController)
                        }
                        composable("configuracion_tema") {
                            ConfiguracionTemaScreen(
                                onTemaSeleccionado = {
                                        navController.popBackStack()
                                }
                            )
                        }
                        composable("historial_partida"){
                            HistorialPartidasScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}