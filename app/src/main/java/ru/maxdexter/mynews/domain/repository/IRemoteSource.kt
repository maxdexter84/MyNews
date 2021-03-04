package ru.maxdexter.mynews.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.domain.models.Resource

interface IRemoteSource {
   suspend fun breakingNews(pageNumber: Int,
                     category: String,
                     pageSize: Int,
                     countryCod: String): Flow<Resource>

   suspend fun searchNews(pageNumber: Int,
                   query: String,
                   pageSize: Int,
                   countryCod: String): Flow<Resource>
}
//Flow<PagingData<Article>>