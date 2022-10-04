package com.example.pocketdiffusion.viewmodels.uimodels

sealed class HomeStateUi {
    class Empty(val data: HomeUiModel) : HomeStateUi()
    object Loading : HomeStateUi()
    class ShouldLogOut(val data: HomeUiModel) : HomeStateUi()
    class Loaded(val data: HomeUiModel) : HomeStateUi()
    class LoadedImage(val data: HomeUiModel) : HomeStateUi()
    class Error(val message: String) : HomeStateUi()
}
