package com.example.pocketdiffusion.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pocketdiffusion.R
import com.example.pocketdiffusion.navigation.BottomNavItem
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

@Composable
fun BitmapImage(bitmap: Bitmap) {
    Image(
        modifier = Modifier.height(512.dp).width(512.dp),
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Generated Image",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Settings,
    )
    NavigationBar(
        contentColor = Pink80
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
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
