package com.echosystem.localshare.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.echosystem.localshare.ui.screens.*

@Composable
fun LocalShareNavHost() {
    val navController = rememberNavController()
    val items = listOf("home", "devices", "files", "settings", "about")
    val icons = listOf(Icons.Default.Home, Icons.Default.Devices, Icons.Default.Folder, Icons.Default.Settings, Icons.Default.Info)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route
                items.forEachIndexed { index, route ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = null) },
                        selected = currentRoute == route,
                        onClick = { navController.navigate(route) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("home") { HomeScreen() }
            composable("devices") { DevicesScreen() }
            composable("files") { FilesScreen() }
            composable("settings") { SettingsScreen() }
            composable("about") { AboutScreen() }
        }
    }
}
