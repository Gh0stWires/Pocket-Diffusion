package com.example.pocketdiffusion.navigation

import com.example.pocketdiffusion.R

sealed class NavRoutes(var title: String, var icon: Int = R.drawable.ic_home, val route: String) {
    object Auth : NavRoutes("auth", route = "auth")
    object Home : NavRoutes("home", R.drawable.ic_home, "home")
    object Settings : NavRoutes("settings", R.drawable.ic_settings, "settings")
}
