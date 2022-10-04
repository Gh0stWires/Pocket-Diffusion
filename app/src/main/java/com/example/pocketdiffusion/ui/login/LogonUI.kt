package com.example.pocketdiffusion.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pocketdiffusion.common.ErrorDialog
import com.example.pocketdiffusion.navigation.NavRoutes
import com.example.pocketdiffusion.ui.theme.Pink40
import com.example.pocketdiffusion.viewmodels.LoginViewModel
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiModel
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.Empty
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.Error
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.Loading
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.LoggedIn
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.SignUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavHostController?,
    paddingValues: PaddingValues
) {
    var state = loginViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .padding(20.dp)
            .padding(paddingValues = paddingValues)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.value) {
            is Empty -> Login(loginUiModel = (state.value as Empty).data, loginViewModel)
            is Error -> ErrorDialog(message = (state.value as Error).message) {
                loginViewModel.resetState()
            }

            Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            is SignUp -> SignUp((state.value as SignUp).data, loginViewModel)
            is LoggedIn -> {
                navController?.navigate(NavRoutes.Home.route) {
                    this.launchSingleTop = true
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(loginUiModel: LoginUiModel, viewModel: LoginViewModel) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Text(text = "Login", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

    Spacer(modifier = Modifier.height(20.dp))
    TextField(
        label = { Text(text = "Email") },
        value = email.value,
        onValueChange = { email.value = it }
    )

    Spacer(modifier = Modifier.height(20.dp))
    TextField(
        label = { Text(text = "Password") },
        value = password.value,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = { password.value = it }
    )

    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
        Button(
            onClick = {
                loginUiModel.function(email.value.text, password.value.text)
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Login")
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Sign up here"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
                loginUiModel.toggleState(viewModel.uiState.value)
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Pink40
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(loginUiModel: LoginUiModel, viewModel: LoginViewModel) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Text(text = "Sign Up", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

    Spacer(modifier = Modifier.height(20.dp))
    TextField(
        label = { Text(text = "Email") },
        value = email.value,
        onValueChange = { email.value = it }
    )

    Spacer(modifier = Modifier.height(20.dp))
    TextField(
        label = { Text(text = "Password") },
        value = password.value,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = { password.value = it }
    )

    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
        Button(
            onClick = {
                loginUiModel.function(email.value.text, password.value.text)
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Sign Up")
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Login here"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
                loginUiModel.toggleState(viewModel.uiState.value)
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Pink40
            )
        )
    }
}
