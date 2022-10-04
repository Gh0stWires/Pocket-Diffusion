@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pocketdiffusion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pocketdiffusion.common.BottomNavigationBar
import com.example.pocketdiffusion.common.TopBar
import com.example.pocketdiffusion.navigation.NavRoutes
import com.example.pocketdiffusion.ui.home.Home
import com.example.pocketdiffusion.ui.login.LoginPage
import com.example.pocketdiffusion.ui.settings.Settings
import com.example.pocketdiffusion.ui.theme.PocketDiffusionTheme
import com.example.pocketdiffusion.viewmodels.HomeViewModel
import com.example.pocketdiffusion.viewmodels.LoginViewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavRoutes.Auth.route) {

        composable(route = NavRoutes.Auth.route, content = {
            Scaffold(
                topBar = { TopBar() },
                content = {
                    val viewModel = hiltViewModel<LoginViewModel>()
                    LoginPage(
                        navController = navController,
                        loginViewModel = viewModel,
                        paddingValues = it
                    )
                }
            )
        })

        composable(NavRoutes.Home.route) {
            Scaffold(
                topBar = { TopBar() },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                },
            ) {
                val viewModel = hiltViewModel<HomeViewModel>()
                Home(
                    navController = navController,
                    homeViewModel = viewModel,
                    paddingValues = it
                )
            }
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
        // MainScreen()
    }
}
