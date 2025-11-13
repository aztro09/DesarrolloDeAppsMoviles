package com.example.memorama.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun MenuPrincipalScreen(navController: NavHostController){
    Column (modifier = Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.spacedBy(16.dp)){
        Text("Memorama", style = MaterialTheme.typography.headlineLarge)

        Button(onClick = {navController.navigate("juego")}) {
            Text("Jugar")
        }

        Button(onClick = {navController.navigate("historial")}) {
            Text("Historial de Partidas")
        }
    }
}