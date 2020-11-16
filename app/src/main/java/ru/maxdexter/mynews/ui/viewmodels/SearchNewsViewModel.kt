package ru.maxdexter.mynews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.maxdexter.mynews.Resource
import ru.maxdexter.mynews.models.NewsResponse
import ru.maxdexter.mynews.repository.NewsRepository

class SearchNewsViewModel(val repository: NewsRepository) : ViewModel() {


    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.IO)
    private val _searchNews = MutableLiveData<Resource<NewsResponse>>()
    val searchNews: LiveData<Resource<NewsResponse>>
        get() = _searchNews
    var searchNewsPage = 1

    init {

    }

    fun getSearchingNews(query:String) = scope.launch {
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