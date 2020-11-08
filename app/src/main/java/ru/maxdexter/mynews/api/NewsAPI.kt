package ru.maxdexter.mynews.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.maxdexter.mynews.util.Constants.Companion.API_KEY
import ru.maxdexter.mynews.data.NewsResponse

interface NewsAPI {

//
//https://newsapi.org/v2/top-headlines?country=us&apiKey=a1a721d908b2412eb541dc137936049e

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(@Query("country") countryCod:String = "ru",
                                @Query("page") pageNumber: Int = 1,
                                @Query("apiKey") apiKey:String = API_KEY,) : Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchForNews(@Query("q") searchQuery:String,
                                @Query("page") pageNumber: Int = 1,
                                @Query("apiKey") apiKey:String = API_KEY,) : Response<NewsResponse>
}