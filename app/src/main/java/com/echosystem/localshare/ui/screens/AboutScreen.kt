package com.echosystem.localshare.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "About", style = MaterialTheme.typography.headlineMedium)
        Text(text = "LocalShare v1.0.0", style = MaterialTheme.typography.bodyLarge)
    }
}
