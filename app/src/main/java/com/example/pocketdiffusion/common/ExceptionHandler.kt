package com.example.pocketdiffusion.common

import androidx.annotation.StringRes
import com.example.pocketdiffusion.R
import java.net.UnknownHostException

internal object ExceptionHandler {

    @StringRes
    fun parse(t: Throwable): Int {
        return when (t) {
            is UnknownHostException -> androidx.compose.ui.R.string.default_error_message
            else -> androidx.compose.ui.R.string.default_error_message
        }
    }
}
