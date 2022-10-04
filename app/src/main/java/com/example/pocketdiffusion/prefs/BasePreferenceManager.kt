package com.example.pocketdiffusion.prefs

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

abstract class BasePreferenceManager {
    abstract suspend fun clear(block: ((Boolean) -> Unit)? = null)

    /**
     * Method to clear all Datastore preferences(Key-Values pairs) provided
     *
     * @param dataStore         array of dataStores
     * @param block             callback function - Optional
     */
    suspend fun clearAll(dataStore: Array<out DataStore<Preferences>>, block: ((Boolean) -> Unit)? = null) {
        try {
            for (data in dataStore) {
                data.edit { it.clear() }
            }
            block?.let {
                withContext(Dispatchers.Main) { it(true) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            block?.let {
                withContext(Dispatchers.Main) { it(false) }
            }
        }
    }

    /**
     * Generic method to save key-value pairs in preferences
     *
     * @param key         Key to store in preference
     * @param value       Generic Typed value to store against key
     * @param dataStore   DataStore preference in which the key-value to be stored
     * @param block       Callback to receive the success/fail storage operation
     */
    suspend inline fun <reified T : Any> save(
        key: String,
        value: T,
        dataStore: DataStore<Preferences>,
        noinline block: ((Boolean) -> Unit)? = null
    ) {
        try {
            val dataStoreKey = stringPreferencesKey(key)
            dataStore.edit { preference ->
                preference[dataStoreKey] = value.toString()
                block?.let {
                    withContext(Dispatchers.IO) { it(true) }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            block?.let {
                withContext(Dispatchers.IO) { it(false) }
            }
        }
    }

    /**
     * Generic method to get values from DataStore preference
     *
     * @param key         Key provide to retrieve value
     * @param datastore   DataStore preference from which the value to be retrieve
     *
     * @return T?          Generic typed value or null if not found
     */
    suspend inline fun <reified T : Any> getValue(key: String, datastore: DataStore<Preferences>): T? {
        return try {
            val dataStoreKey = stringPreferencesKey(key)
            val preferences = datastore.data.first()
            preferences[dataStoreKey] as T
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Generic method to get values from DataStore preference in Flow typed
     *
     * @param key         Key provide to retrieve value
     * @param datastore   DataStore preference from which the value to be retrieve
     *
     * @return Flow<T>     Generic typed value as flow
     */
    suspend fun <T> getValueAsFlow(key: String, datastore: DataStore<Preferences>): Flow<T> =
        datastore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e("TAG", "getValueFlow: $exception")
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preference ->
                val dataStoreKey = stringPreferencesKey(key)
                preference[dataStoreKey] as T
            }
}

fun Context.createDataStore(
    name: String,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    migrations: List<DataMigration<Preferences>> = listOf(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
): DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
        corruptionHandler = corruptionHandler,
        migrations = migrations,
        scope = scope
    ) {
        File(this.filesDir, "datastore/$name.preferences_pb")
    }
