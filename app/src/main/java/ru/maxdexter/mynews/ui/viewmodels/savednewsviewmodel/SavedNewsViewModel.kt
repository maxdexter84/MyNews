package ru.maxdexter.mynews.ui.viewmodels.savednewsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.data.entity.db.Bookmark
import ru.maxdexter.mynews.domain.mappers.UIModelBookmark
import ru.maxdexter.mynews.domain.repository.INewsRepository
import ru.maxdexter.mynews.ui.entity.UIModel

class SavedNewsViewModel(val repository: INewsRepository) : ViewModel() {

    private val _savedBookmarks = MutableLiveData<List<UIModel>>(emptyList())
        val savedBookmarks: LiveData<List<UIModel>>
        get() = _savedBookmarks
    
    init {
        loadBookmarks()
    }
    
    private fun loadBookmarks(){
        viewModelScope.launch {
            repository.loadBookmark().collect { list ->
               _savedBookmarks.value = list.map { UIModelBookmark().fromBookmarkToUIModel(it) }
            }
        }
    }
    

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    fun saveBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.saveBookmark(bookmark)
        }
    }




}