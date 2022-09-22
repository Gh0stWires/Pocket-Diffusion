package com.example.pocketdiffusion.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Settings : NavRoutes("settings")
}
