package com.echosystem.localshare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF00D4FF),
    background = Color(0xFF0A0A0F),
    surface = Color(0xFF141420)
)

@Composable
fun LocalShareTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColors, content = content)
}
