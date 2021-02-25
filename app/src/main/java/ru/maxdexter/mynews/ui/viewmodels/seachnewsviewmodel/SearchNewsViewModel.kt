package ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.maxdexter.mynews.models.Resource
import ru.maxdexter.mynews.models.NewsResponse
import ru.maxdexter.mynews.repository.NewsRepository

class SearchNewsViewModel(val repository: NewsRepository) : ViewModel() {

    private val _searchNews = MutableLiveData<Resource<NewsResponse>>()
            val searchNews: LiveData<Resource<NewsResponse>>
            get() = _searchNews
    private var searchNewsPage = 1



    fun getSearchingNews(query:String) = viewModelScope.launch {
        _searchNews.postValue(Resource.Loading()) //Состояние загрузки
        val response = repository.searchNews(query,searchNewsPage) //Суспенд функция для запроса в сеть
        _searchNews.postValue(handleSearchNewsResponse(response))  //Полученый ответ проверяется на успешность и данные присваваються ливдате
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