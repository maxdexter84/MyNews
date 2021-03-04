package ru.maxdexter.mynews.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.maxdexter.mynews.domain.common.Constants.Companion.API_KEY
import ru.maxdexter.mynews.data.entity.api.NewsResponse

interface NewsAPI {

//
//https://newsapi.org/v2/top-headlines?country=us&apiKey=a1a721d908b2412eb541dc137936049e

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(@Query("country") countryCod:String ,
                                @Query("page") pageNumber: Int,
                                @Query("category") category: String = "general",
                                @Query("pageSize") pageSize: Int = 20,
                                @Query("apiKey") apiKey:String = API_KEY,) : NewsResponse


    @GET("v2/everything")
    suspend fun searchingNews(@Query("q") searchQuery:String,
                              @Query("language") countryCod:String ,
                              @Query("page") pageNumber: Int ,
                              @Query("pageSize") pageSize: Int = 20,
                              @Query("apiKey") apiKey:String = API_KEY,) : NewsResponse
}