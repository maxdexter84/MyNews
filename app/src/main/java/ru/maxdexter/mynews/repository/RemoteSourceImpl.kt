package ru.maxdexter.mynews.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.maxdexter.mynews.data.api.NewsAPI
import ru.maxdexter.mynews.data.mappers.ArticleToUIModel
import ru.maxdexter.mynews.domain.models.Resource
import ru.maxdexter.mynews.domain.repository.IRemoteSource

class RemoteSourceImpl (private val api: NewsAPI): IRemoteSource {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun breakingNews(
        pageNumber: Int,
        category: String,
        pageSize: Int,
        countryCod: String
    ): Flow<Resource> = flow{
        try {
            val response = api.getBreakingNews(countryCod, pageNumber, category, pageSize)
            val list = response.articles.map { ArticleToUIModel().fromArticlesToUIModel(it) }
            emit(Resource.Success(list))
        }catch (e: HttpException){
            emit(Resource.Error(e.message()))
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchNews(
        pageNumber: Int,
        query: String,
        pageSize: Int,
        countryCod: String
    ): Flow<Resource> = flow{
       try {
           val response = api.searchingNews(query,countryCod, pageNumber, pageSize)
           val list = response.articles.map { ArticleToUIModel().fromArticlesToUIModel(it) }
           emit(Resource.Success(list))
       }catch (e: HttpException){
           emit(Resource.Error(e.message()))
       }
    }
}