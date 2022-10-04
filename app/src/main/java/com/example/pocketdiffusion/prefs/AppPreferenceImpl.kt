package com.example.pocketdiffusion.prefs

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferenceImpl @Inject constructor(@ApplicationContext context: Context) : AppPreference {

    companion object {
        const val TOKEN = "token"
    }

    private var preference = context.getSharedPreferences("dagger-pref", Context.MODE_PRIVATE)
    private var editor = preference.edit()

    override var apiToken: String
        get() = getString(TOKEN)
        set(value) {
            saveString(TOKEN, value)
        }

    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }
}
