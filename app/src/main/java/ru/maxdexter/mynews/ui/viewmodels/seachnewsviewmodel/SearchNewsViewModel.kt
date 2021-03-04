package ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.domain.repository.INewsRepository
import ru.maxdexter.mynews.ui.entity.UIModel
import ru.maxdexter.mynews.ui.paging.SearchNewsPagingSource
import ru.maxdexter.mynews.util.Constants

class SearchNewsViewModel(val repository: INewsRepository) : ViewModel() {
    private var currentQuery: String? = null
    private var currentCountryCode: String? = null
    var currentSearchResult: Flow<PagingData<UIModel>>? = null

    
    fun getSearchingNews(query:String, countryCode: String): Flow<PagingData<UIModel>> {
        val lastResult = currentSearchResult
        if (lastResult != null && query == currentQuery && countryCode == currentCountryCode) {
            return lastResult
        }
        currentCountryCode = countryCode
        currentQuery = query
        val newResult = pagingSearchNews(query,countryCode).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    private fun pagingSearchNews(query: String, countryCode: String): Flow<PagingData<UIModel>>{
        return Pager(config = PagingConfig(pageSize = Constants.PAGE_SIZE,enablePlaceholders = false),
            pagingSourceFactory = { SearchNewsPagingSource(repository,countryCode,query) }).flow
    }
   
}