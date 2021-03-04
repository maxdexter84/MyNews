package ru.maxdexter.mynews.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.data.entity.db.Bookmark
import ru.maxdexter.mynews.domain.models.Resource

interface INewsRepository {
    suspend fun loadBreakingNews(pageNumber: Int,
                         category: String,
                         pageSize: Int,
                         countryCod: String): Flow<Resource>
    suspend fun loadSearchNews(pageNumber: Int,
                       query: String,
                       pageSize: Int,
                       countryCod: String): Flow<Resource>
    suspend fun loadBookmark(): Flow<List<Bookmark>>
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun saveBookmark(bookmark: Bookmark)
}