package com.example.pocketdiffusion.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.pocketdiffusion.prefs.AuthPreferenceManager.Keys.AUTH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private var preference = context.getSharedPreferences("dagger-pref", Context.MODE_PRIVATE)
    private var editor = preference.edit()

    var apiToken: String
        get() = getString(AUTH_TOKEN)
        set(value) {
            saveString(AUTH_TOKEN, value)
        }

    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    object Keys {
        const val AUTH_TOKEN = "AUTH_TOKEN"
    }
}
