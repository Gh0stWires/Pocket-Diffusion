package com.example.pocketdiffusion.common

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pocketdiffusion.R
import com.example.pocketdiffusion.navigation.NavRoutes
import com.example.pocketdiffusion.ui.theme.Pink40
import com.example.pocketdiffusion.ui.theme.Pink80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChange(text)
        },
        singleLine = false,
        label = { Text(title) },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(
            color = Pink40,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavHostController, title: String, showBackIcon: Boolean) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (showBackIcon && navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                null
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BitmapImage(bitmap: Bitmap, context: Context) {
    Image(
        modifier = Modifier.height(512.dp).width(512.dp).combinedClickable(
            onClick = { },
            onLongClick = {
                context.shareImage("generated_Image", bitmap, "pocket_diffusion_generated")
            },
        ),
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Generated Image",
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavRoutes.Home,
        NavRoutes.Settings,
    )
    NavigationBar(
        contentColor = Pink80
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.map {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(
                        text = it.title
                    )
                },
                selected = currentRoute == it.route,
                selectedContentColor = Pink40,
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },

            )
        }
    }
}

@Composable
fun ErrorDialog(message: String, setState: () -> Unit) {
    Column {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(message)
                },
                confirmButton = {
                    Button(

                        onClick = {
                            setState()
                            openDialog.value = false
                        }
                    ) {
                        Text("Try again")
                    }
                },
                dismissButton = {
                    Button(

                        onClick = {
                            setState()
                            openDialog.value = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    // BottomNavigationBar()
}
@Preview
@Composable
fun ComposablePreview() {
    CustomTextField("test", "") {}
}
