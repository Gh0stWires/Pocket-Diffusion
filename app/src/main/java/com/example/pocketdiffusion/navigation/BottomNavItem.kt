package com.example.pocketdiffusion.navigation

import com.example.pocketdiffusion.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Settings: BottomNavItem("Settings",R.drawable.ic_settings,"settings")
}