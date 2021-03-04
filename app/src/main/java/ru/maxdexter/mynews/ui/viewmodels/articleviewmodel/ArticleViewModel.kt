package ru.maxdexter.mynews.ui.viewmodels.articleviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.domain.mappers.UIModelBookmark
import ru.maxdexter.mynews.domain.repository.INewsRepository
import ru.maxdexter.mynews.ui.entity.UIModel

class ArticleViewModel(val repository: INewsRepository) : ViewModel(){
    
    fun saveArticle(uiModel: UIModel) {
        viewModelScope.launch {
            repository.saveBookmark(UIModelBookmark().fromUIModelToUIBookmark(uiModel))
        }
    }
}