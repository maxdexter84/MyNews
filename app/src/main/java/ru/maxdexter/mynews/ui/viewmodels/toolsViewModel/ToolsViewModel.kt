package ru.maxdexter.mynews.ui.viewmodels.toolsViewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.settings.AppPreferences

class ToolsViewModel(private val preferences: AppPreferences) : ViewModel() {

    fun setTheme(isDarkTheme: Boolean) {
        preferences.isDarkTheme = isDarkTheme
    }

}