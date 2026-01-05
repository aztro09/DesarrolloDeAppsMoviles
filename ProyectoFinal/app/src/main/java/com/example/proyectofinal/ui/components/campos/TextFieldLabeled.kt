package com.example.proyectofinal.ui.components.campos

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun TextFieldLabeled(value: String, onValueChange: (String) -> Unit, label: String){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
    )
}