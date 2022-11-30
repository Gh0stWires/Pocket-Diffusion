package com.example.pocketdiffusion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.api.CoroutineDispatcherProvider
import com.example.domain.api.repository.LocalRepo
import com.example.pocketdiffusion.prefs.AuthPreferenceManager
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiModel
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.Empty
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.Error
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.Loading
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.LoggedIn
import com.example.pocketdiffusion.viewmodels.uimodels.LoginUiState.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LocalRepo,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val authPreferenceManager: AuthPreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(
        LoginUiState.Empty(
            LoginUiModel("", "", function = { email, password ->
                login(email, password)
            }, toggleState = {
                changeState(it)
            })
        )
    )

    fun resetState() {
        _uiState.value = LoginUiState.Empty(
            LoginUiModel("", "", function = { email, password ->
                login(email, password)
            }, toggleState = {
                changeState(it)
            })
        )
    }

    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            if (authPreferenceManager.apiToken != "") {
                _uiState.value = LoginUiState.LoggedIn(
                    LoginUiModel(
                        "",
                        "",
                        function = { _, _ ->
                        }, toggleState = {
                    }
                    )
                )
            } else {
                _uiState.value = LoginUiState.Empty(
                    LoginUiModel("", "", function = { email, password ->
                        login(email, password)
                    }, toggleState = {
                        changeState(it)
                    })
                )
            }
        }
    }

    private fun changeState(state: LoginUiState) {
        if (state is Empty) {
            _uiState.value = LoginUiState.SignUp(
                LoginUiModel("", "", function = { email, password ->
                    signUp(email, password)
                }, toggleState = {
                    changeState(it)
                })
            )
        } else {
            _uiState.value = LoginUiState.Empty(
                LoginUiModel("", "", function = { email, password ->
                    login(email, password)
                }, toggleState = {
                    changeState(it)
                })
            )
        }
    }

    private fun signUp(email: String, password: String) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.signUp(email, password)
                if (response.message == "Created") {
                    login(email, password)
                } else {
                    _uiState.value = LoginUiState.Error(message = response.message ?: "")
                }
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    _uiState.value = LoginUiState.Error(message = ex.message ?: "")
                } else {
                    _uiState.value = LoginUiState.Error(message = ex.message ?: "")
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.login(email, password)
                if (response.message == null) {
                    authPreferenceManager.apiToken = response.token ?: ""
                    _uiState.value = LoginUiState.LoggedIn(
                        LoginUiModel(
                            "",
                            "",
                            function = { _, _ ->
                            }, toggleState = {
                            }
                        )
                    )
                } else {
                    _uiState.value = LoginUiState.Error(message = response.message ?: "")
                }
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    // TODO
                } else {
                    // TODO
                }
            }
        }
    }
}
