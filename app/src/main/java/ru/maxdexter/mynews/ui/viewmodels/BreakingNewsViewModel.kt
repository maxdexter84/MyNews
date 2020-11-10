package ru.maxdexter.mynews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import ru.maxdexter.mynews.Resource
import ru.maxdexter.mynews.models.NewsResponse
import ru.maxdexter.mynews.repository.NewsRepository

class BreakingNewsViewModel (val repository: NewsRepository): ViewModel() {

    private val job = SupervisorJob()
    val scope = CoroutineScope(job + Dispatchers.IO)
    private val _breakingNews = MutableLiveData<Resource<NewsResponse>>()
            val breakingNews: LiveData<Resource<NewsResponse>>
            get() = _breakingNews

    var breakingNewsPage = 1

    init {
        getBreakingNews("ru")
    }

    fun getBreakingNews(countryCode:String) = scope.launch {
        _breakingNews.postValue(Resource.Loading()) //Состояние загрузки
        val response = repository.getBreakingNews(countryCode,breakingNewsPage) //Суспенд функция для запроса в сеть
        _breakingNews.postValue(handleBreakingNews(response))  //Полученый ответ проверяется на успешность и данные присваваються ливдате
    }


    private fun handleBreakingNews(response: Response<NewsResponse>) :Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}