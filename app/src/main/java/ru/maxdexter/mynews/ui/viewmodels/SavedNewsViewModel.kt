package ru.maxdexter.mynews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.repository.NewsRepository

class SavedNewsViewModel(val repository: NewsRepository) : ViewModel() {

   val savedArticle = repository.getSavedArticle()


    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }




}