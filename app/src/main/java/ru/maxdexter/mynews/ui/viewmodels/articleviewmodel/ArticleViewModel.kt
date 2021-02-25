package ru.maxdexter.mynews.ui.viewmodels.articleviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.repository.INewsRepository
import ru.maxdexter.mynews.repository.NewsRepository

class ArticleViewModel(val repository: INewsRepository) : ViewModel(){



    fun saveArticle(article: Article) {
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }
}