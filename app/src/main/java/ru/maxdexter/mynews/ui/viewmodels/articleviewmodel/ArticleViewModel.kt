package ru.maxdexter.mynews.ui.viewmodels.articleviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.repository.NewsRepository

class ArticleViewModel(val repository: NewsRepository) : ViewModel(){



    fun saveArticle(article: Article) {
        viewModelScope.launch {
            repository.db.getArticleDao().insert(article)
        }
    }
}