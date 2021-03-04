package ru.maxdexter.mynews.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.data.entity.db.Bookmark

interface ILocalSource {
    suspend fun saveBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    fun getAllBookmark(): Flow<List<Bookmark>>
}