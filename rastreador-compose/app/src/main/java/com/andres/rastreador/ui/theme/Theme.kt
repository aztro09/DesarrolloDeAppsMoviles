
package com.andres.rastreador.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

// Guinda IPN
private val GuindaLight = lightColorScheme(
    primary = Color(0xFF7A0034),
    onPrimary = Color.White,
    secondary = Color(0xFFB0004E),
    onSecondary = Color.White,
    background = Color(0xFFFFF8F9),
    onBackground = Color(0xFF201A1A),
    surface = Color(0xFFFFF8F9),
    onSurface = Color(0xFF201A1A)
)
private val GuindaDark = darkColorScheme(
    primary = Color(0xFFD67CA7),
    onPrimary = Color.Black,
    secondary = Color(0xFFE3A3C1),
    onSecondary = Color.Black,
    background = Color(0xFF151012),
    onBackground = Color(0xFFECE0E2),
    surface = Color(0xFF151012),
    onSurface = Color(0xFFECE0E2)
)

// Azul ESCOM
private val AzulLight = lightColorScheme(
    primary = Color(0xFF0057B8),
    onPrimary = Color.White,
    secondary = Color(0xFF1E88E5),
    onSecondary = Color.White,
    background = Color(0xFFF7FAFF),
    onBackground = Color(0xFF0B1B2B),
    surface = Color(0xFFF7FAFF),
    onSurface = Color(0xFF0B1B2B)
)
private val AzulDark = darkColorScheme(
    primary = Color(0xFF87B6FF),
    onPrimary = Color.Black,
    secondary = Color(0xFFAAD1FF),
    onSecondary = Color.Black,
    background = Color(0xFF0B1B2B),
    onBackground = Color(0xFFE9F2FF),
    surface = Color(0xFF0B1B2B),
    onSurface = Color(0xFFE9F2FF)
)

@Composable
fun AppTheme(themeName: String, content: @Composable () -> Unit) {
    val dark = isSystemInDarkTheme()
    val colors = when (themeName) {
        "AZUL" -> if (dark) AzulDark else AzulLight
        else -> if (dark) GuindaDark else GuindaLight
    }
    MaterialTheme(colorScheme = colors, content = content)
}
