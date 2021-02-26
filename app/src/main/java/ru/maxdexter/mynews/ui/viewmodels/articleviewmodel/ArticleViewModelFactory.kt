package ru.maxdexter.mynews.ui.viewmodels.articleviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.repository.INewsRepository
import ru.maxdexter.mynews.repository.NewsRepository

class ArticleViewModelFactory(private val repository: INewsRepository) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(repository) as T
    }
}