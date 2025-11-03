package com.example.memorama.model

import Carta
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp

@Composable
fun CartaItem(carta: Carta, onClick: () -> Unit) {
    val backgroundColor = when {
        carta.estaEmparejada -> Color.Green
        carta.estaVolteada -> Color.LightGray
        else -> Color.DarkGray
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(backgroundColor)
            .clickable(enabled = !carta.estaEmparejada && !carta.estaVolteada, onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        if(carta.estaVolteada || carta.estaEmparejada){
            Text(
                text = carta.valor.toString(),
                style = MaterialTheme.typography.titleLarge
            )
        }else{
            Text("?", style = MaterialTheme.typography.titleLarge)
        }
    }

    var volteando by remember {mutableStateOf(false)}

    val rotation by animateFloatAsState(
        targetValue = if(volteando || carta.estaVolteada || carta.estaEmparejada) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        finishedListener = {
            volteando = false
        }
    )

    Box(
        modifier = Modifier
            .size(80.dp)
            .graphicsLayer{
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable(enabled = !carta.estaEmparejada && !carta.estaVolteada){
                volteando = true
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        if(rotation <= 90f){
            //REVERSO DE CARTA
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ){
                //FRENTE DE CARTA
                Text(carta.valor.toString(), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}
