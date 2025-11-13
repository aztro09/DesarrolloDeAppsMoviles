package com.example.memorama.ui.navigation

import PantallaJuego
import HistorialPartidasScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.memorama.ui.ConfiguracionTemaScreen
import com.example.memorama.ui.MenuPrincipalScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuPrincipalScreen(navController)
        }
        composable("juego"){
            PantallaJuego(navController = navController)
        }
        composable("configuracion_tema"){
            ConfiguracionTemaScreen(
                onTemaSeleccionado = {
                    navController.popBackStack()
                }
            )
        }
        composable("historial"){
            HistorialPartidasScreen()
        }
    }
}
