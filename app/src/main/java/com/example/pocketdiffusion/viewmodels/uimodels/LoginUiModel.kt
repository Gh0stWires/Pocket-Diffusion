package com.example.pocketdiffusion.viewmodels.uimodels

data class LoginUiModel(
    val email: String? = null,
    val password: String? = null,
    var function: (String, String) -> Unit,
    val toggleState: (LoginUiState) -> Unit,
    var message: String = ""
)
