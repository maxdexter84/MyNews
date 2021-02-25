package ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.models.Resource
import ru.maxdexter.mynews.models.NewsResponse
import ru.maxdexter.mynews.repository.INewsRepository

class BreakingNewsViewModel (val repository: INewsRepository): ViewModel() {
    private var currentCountryCode: String? = null
    private var currentCategory: String? = null
    private var currentSearchResult: Flow<PagingData<Article>>? = null
    private val _breakingNews = MutableLiveData<Resource<NewsResponse>>()
            val breakingNews: LiveData<Resource<NewsResponse>>
            get() = _breakingNews

    init {
        getBreakingNews("ru","general")
    }

    private fun getBreakingNews(countryCode:String, category: String): Flow<PagingData<Article>>{
        val lastResult = currentSearchResult
        if (lastResult != null && currentCategory == category && currentCountryCode == countryCode){
            return lastResult
        }
        currentCategory = category
        currentCountryCode = countryCode
        val newResult = repository.getBreakingNews(countryCode,category).cachedIn(viewModelScope) //cachedIn кеширует данные из результатов запроса
        currentSearchResult = newResult
        return newResult
    }



    private fun handleBreakingNews(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}