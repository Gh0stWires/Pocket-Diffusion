@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pocketdiffusion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pocketdiffusion.common.BottomNavigationBar
import com.example.pocketdiffusion.common.TopBar
import com.example.pocketdiffusion.navigation.BottomNavItem
import com.example.pocketdiffusion.navigation.NavRoutes
import com.example.pocketdiffusion.ui.home.Home
import com.example.pocketdiffusion.ui.settings.Settings
import com.example.pocketdiffusion.ui.theme.PocketDiffusionTheme
import com.example.pocketdiffusion.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketDiffusionTheme {
                MainScreen()
            }
        }
    }
}



@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        content = {
            NavigationGraph(navController = navController, it)
                  },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(NavRoutes.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            Home(navController = navController, homeViewModel = viewModel, paddingValues = paddingValues)
        }

        composable(NavRoutes.Settings.route) {
            Settings(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PocketDiffusionTheme {
        MainScreen()
    }
}