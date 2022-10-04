package com.example.pocketdiffusion.viewmodels.uimodels

sealed class LoginUiState {
    class Empty(val data: LoginUiModel) : LoginUiState()
    class SignUp(val data: LoginUiModel) : LoginUiState()
    object Loading : LoginUiState()
    class Error(val message: String) : LoginUiState()
    class LoggedIn(val data: LoginUiModel) : LoginUiState()
}
