package ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.models.Resource
import ru.maxdexter.mynews.models.NewsResponse
import ru.maxdexter.mynews.repository.INewsRepository
import ru.maxdexter.mynews.repository.NewsRepository

class SearchNewsViewModel(val repository: INewsRepository) : ViewModel() {
    private var currentQuery: String? = null
    private var currentCountryCode: String? = null
    var currentSearchResult: Flow<PagingData<Article>>? = null

    private val _searchNews = MutableLiveData<Resource<NewsResponse>>()
            val searchNews: LiveData<Resource<NewsResponse>>
            get() = _searchNews



    fun getSearchingNews(query:String, countryCode: String): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        if (lastResult != null && query == currentQuery && countryCode == currentCountryCode) {
            return lastResult
        }
        currentCountryCode = countryCode
        currentQuery = query
        val newResult = repository.searchNews(query,countryCode).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult

    }
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}