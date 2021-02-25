package ru.maxdexter.mynews.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.models.Article

interface INewsRepository {

     fun getBreakingNews(countryCode: String, category: String): Flow<PagingData<Article>>
     fun searchNews(query: String, countryCode: String): Flow<PagingData<Article>>
    suspend fun saveArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getSavedArticle(): LiveData<List<Article>>
}