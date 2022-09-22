package com.example.pocketdiffusion.viewmodels.uimodels

import android.graphics.Bitmap

data class HomeUiModel(
    var promptSent: Boolean = false,
    var prompt: String? = "",
    var bitmap: Bitmap?,
    var function: (String) -> Unit
)
