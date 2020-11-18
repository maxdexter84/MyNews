package ru.maxdexter.mynews.settings

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.preferencesSetKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.maxdexter.mynews.extensions.createDataStore
import java.io.IOException

class AppPreferences(context: Context) {

    val preferencesStore = context.createDataStore("app_preferences")


    private object PreferencesKeys {
        val isDARK_THEME = preferencesKey<Boolean>("is_dark_theme")
    }

    val appPreferencesFlow: Flow<AppSettings> = preferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {preferences ->
            val isDarkTheme = preferences[PreferencesKeys.isDARK_THEME] ?: false
            AppSettings(isDarkTheme)
        }

    suspend fun updateTheme(isDarkTheme: Boolean) {
        preferencesStore.edit {preferences ->
            preferences[PreferencesKeys.isDARK_THEME] = isDarkTheme
        }
    }
}

