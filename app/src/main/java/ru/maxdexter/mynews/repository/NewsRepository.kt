package ru.maxdexter.mynews.repository

import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.data.entity.db.Bookmark
import ru.maxdexter.mynews.domain.models.Resource
import ru.maxdexter.mynews.domain.repository.ILocalSource
import ru.maxdexter.mynews.domain.repository.INewsRepository
import ru.maxdexter.mynews.domain.repository.IRemoteSource


class NewsRepository(private val localSource: ILocalSource, private val remoteSource: IRemoteSource) :
    INewsRepository {
    override suspend fun loadBreakingNews(
        pageNumber: Int,
        category: String,
        pageSize: Int,
        countryCod: String
    ): Flow<Resource> {
       return remoteSource.breakingNews(pageNumber, category, pageSize, countryCod)
    }

    override suspend fun loadSearchNews(
        pageNumber: Int,
        query: String,
        pageSize: Int,
        countryCod: String
    ): Flow<Resource> {
        return remoteSource.searchNews(pageNumber, query, pageSize, countryCod)
    }

    override suspend fun loadBookmark(): Flow<List<Bookmark>> {
       return localSource.getAllBookmark()
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
       localSource.deleteBookmark(bookmark)
    }

    override suspend fun saveBookmark(bookmark: Bookmark) {
        localSource.saveBookmark(bookmark)
    }


}