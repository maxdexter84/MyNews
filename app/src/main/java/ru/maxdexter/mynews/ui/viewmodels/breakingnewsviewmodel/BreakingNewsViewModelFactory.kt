package ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.domain.repository.INewsRepository

class BreakingNewsViewModelFactory(private val repository: INewsRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreakingNewsViewModel(repository) as T
    }
}