package com.example.memorama.ui

import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.ext.capitalize
import com.example.memorama.theme.*
import kotlinx.coroutines.launch

@Composable
fun ConfiguracionTemaScreen(
    context: Context = LocalContext.current,
    onTemaSeleccionado: () -> Unit
){
    val scope = rememberCoroutineScope()
    val temas = listOf("guinda", "azul", "claro", "oscuro")
    val temaPreview = mapOf(
        "guinda" to temaGuinda,
        "azul" to temaAzul,
        "claro" to temaClaro,
        "oscuro" to temaOscuro
    )

    var temaActual by remember { mutableStateOf("guinda") }

    LaunchedEffect(Unit) {
        ThemeManager.obtenerTema(context).collect { temaActual = it }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Configuración de Tema", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        temas.forEach { tema ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        scope.launch {
                            ThemeManager.guardarTema(context, tema)
                            onTemaSeleccionado()
                        }
                    },
                colors = CardDefaults.cardColors(containerColor = temaPreview[tema]!!.primary)
            ){
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tema ${tema.capitalize()}",
                        color = temaPreview[tema]!!.onPrimary
                    )
                }
            }
        }
    }
}