package com.example.memorama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import com.example.memorama.theme.temaAzul
import com.example.memorama.theme.temaGuinda
import com.example.memorama.theme.ThemeManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val temaFlow = ThemeManager.obtenerTema(this)

        setContent {
            val temaSeleccionado by temaFlow.collectAsState(initial = "guinda")
            val colores = when (temaSeleccionado) {
                "azul" -> temaAzul
                else -> temaGuinda
            }

            val snackbarHostState = remember { SnackbarHostState() }

            MaterialTheme(colorScheme = colores) {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    floatingActionButton = {
                        androidx.compose.material3.FloatingActionButton(onClick = {
                            // Ejemplo de Snackbar en Compose
                            LaunchedEffect(Unit) {
                                snackbarHostState.showSnackbar("Acción personalizada")
                            }
                        }) {
                            androidx.compose.material3.Text("FAB")
                        }
                    }
                ) {
                    PantallaJuego()
                }
            }
        }
    }
}