package com.example.pocketdiffusion.viewmodels

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.api.CoroutineDispatcherProvider
import com.example.domain.api.repository.LocalRepo
import com.example.pocketdiffusion.prefs.AuthPreferenceManager
import com.example.pocketdiffusion.viewmodels.uimodels.HomeStateUi
import com.example.pocketdiffusion.viewmodels.uimodels.HomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: LocalRepo,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val authPreferenceManager: AuthPreferenceManager
) : ViewModel() {

    init {
    }

    private val _uiState = MutableStateFlow<HomeStateUi>(
        HomeStateUi.Empty(
            HomeUiModel(false, "", null) {
                sendPrompt(it)
            }
        )
    )
    val uiState: StateFlow<HomeStateUi> = _uiState

    private fun sendPrompt(prompt: String) {
        _uiState.value = HomeStateUi.Loading

        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val token = authPreferenceManager.apiToken
                val response = repository.makeImage(prompt, token)
                if (response.message == "Invalid token provided.") {
                    authPreferenceManager.apiToken = ""
                    _uiState.value = HomeStateUi.ShouldLogOut(
                        HomeUiModel(false, "", null) {
                            sendPrompt(it)
                        }
                    )
                } else {
                    _uiState.value = HomeStateUi.Loaded(
                        HomeUiModel(true, response.result, null) {
                            checkStatus()
                        }
                    )
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

    private fun checkStatus() {
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.statusCheck(authPreferenceManager.apiToken)
                _uiState.value = HomeStateUi.Loaded(
                    HomeUiModel(true, response.status, null) {
                        if (response.status == "done") {
                            getLatestImage()
                        } else {
                            checkStatus()
                        }
                    }
                )
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    // TODO
                } else {
                    // TODO
                }
            }
        }
    }

    private fun restartState() {
        _uiState.value = HomeStateUi.Empty(
            HomeUiModel(false, "", null) {
                sendPrompt(it)
            }
        )
    }

    private fun getLatestImage() {
        _uiState.value = HomeStateUi.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.getLatest(authPreferenceManager.apiToken)

                // display the image data in a ImageView or save it
                val bmp = BitmapFactory.decodeStream(response.byteStream())
                _uiState.value = HomeStateUi.LoadedImage(
                    HomeUiModel(true, "", bmp) {
                        restartState()
                    }
                )
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
