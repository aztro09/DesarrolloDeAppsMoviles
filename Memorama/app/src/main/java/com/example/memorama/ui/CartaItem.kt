package com.example.memorama.model

import Carta
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CartaItem(carta: Carta, onClick: () -> Unit) {
    val backgroundColor = when {
        carta.estaEmparejada -> Color.Green
        carta.estaVolteada -> Color.LightGray
        else -> Color.DarkGray
    }

    val borderColor = if(carta.estaEmparejada) Color.Yellow else Color.White

    Box(
        modifier = Modifier
            .size(80.dp)
            .border(2.dp, borderColor, shape = RoundedCornerShape(8.dp))
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable(enabled = !carta.estaEmparejada && !carta.estaVolteada, onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        if(carta.estaVolteada || carta.estaEmparejada){
            Text(
                text = carta.valor.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }else{
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Help,
                contentDescription = "Carta oculta",
                tint = Color.White
            )
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
