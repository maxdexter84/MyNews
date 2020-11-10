package ru.maxdexter.mynews.repository

import android.content.Context
import retrofit2.Response
import ru.maxdexter.mynews.api.RetrofitInstance
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.models.NewsResponse

class NewsRepository(val db: ArticleDatabase) {


    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
    }



}