package ru.maxdexter.mynews.ui.viewmodels.toolsViewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.settings.AppPreferences

class ToolsViewModelFactory(private val preferences: AppPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToolsViewModel(preferences) as T
    }
}