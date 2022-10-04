package com.example.pocketdiffusion.ui.home

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pocketdiffusion.R
import com.example.pocketdiffusion.common.BitmapImage
import com.example.pocketdiffusion.common.CustomTextField
import com.example.pocketdiffusion.navigation.NavRoutes
import com.example.pocketdiffusion.ui.theme.Pink80
import com.example.pocketdiffusion.ui.theme.PocketDiffusionTheme
import com.example.pocketdiffusion.viewmodels.HomeViewModel
import com.example.pocketdiffusion.viewmodels.uimodels.HomeStateUi
import com.example.pocketdiffusion.viewmodels.uimodels.HomeUiModel

@Composable
fun Home(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val heightInPx = with(LocalDensity.current) {
        LocalConfiguration.current
            .screenHeightDp.dp.toPx()
    }
    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(Color.Transparent, Color.Black, Pink80),
                    0f,
                    heightInPx * 1.1f
                )
            )
            .padding(paddingValues = paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        when (val state = homeViewModel.uiState.collectAsState().value) {
            is HomeStateUi.Empty -> EmptyScreen(data = state.data)
            is HomeStateUi.Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            is HomeStateUi.Error -> {} // ErrorDialog(state.message)
            is HomeStateUi.Loaded -> LoadedScreen(state.data)
            is HomeStateUi.LoadedImage -> LatestImageScreen(state.data)
            is HomeStateUi.ShouldLogOut -> {
                navController.navigate(NavRoutes.Auth.route) {
                    this.launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun LoadedScreen(data: HomeUiModel) {
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = data.prompt ?: "")
        ElevatedButton(
            onClick = { data.function("") }
        ) {
            Text(text = if (data.prompt == "RUNNING") "Check Image Status" else "Get Latest Image")
        }
    }
}

@Composable
fun LatestImageScreen(data: HomeUiModel) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        BitmapImage(
            bitmap = (data.bitmap ?: painterResource(R.drawable.error)) as Bitmap, context
        )
        ElevatedButton(
            onClick = { data.function(data.prompt ?: "") }
        ) {
            Text(text = "Start another Image")
        }
    }
}

@Composable
fun EmptyScreen(data: HomeUiModel) {
    val onPromptChanged = { text: String ->
        data.prompt = text
    }

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        CustomTextField(
            title = "What wonders will you make?",
            textState = data.prompt ?: "",
            onTextChange = onPromptChanged
        )
        ElevatedButton(
            modifier = Modifier.padding(44.dp),
            onClick = { data.function(data.prompt ?: "") }
        ) {
            Text(text = "Start Stable Diffusion Job")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PocketDiffusionTheme {
        Column() {
            EmptyScreen(data = HomeUiModel(false, "", null) {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PocketDiffusionTheme {
        Column() {
            LoadedScreen(data = HomeUiModel(true, "success", null) {})
        }
    }
}
