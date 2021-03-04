package ru.maxdexter.mynews.ui.viewmodels.toolsViewModel


import androidx.lifecycle.ViewModel
import ru.maxdexter.mynews.settings.AppPreferences

class ToolsViewModel(private val preferences: AppPreferences) : ViewModel() {

    fun setTheme(isDarkTheme: Boolean) {
        preferences.isDarkTheme = isDarkTheme
    }

}