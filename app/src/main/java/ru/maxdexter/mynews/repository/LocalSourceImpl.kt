package ru.maxdexter.mynews.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.maxdexter.mynews.data.db.BookmarkDao
import ru.maxdexter.mynews.data.entity.db.Bookmark
import ru.maxdexter.mynews.domain.repository.ILocalSource

class LocalSourceImpl(private val bookmarkDao: BookmarkDao) : ILocalSource {
    override suspend fun saveBookmark(bookmark: Bookmark) {
        withContext(Dispatchers.IO){
            bookmarkDao.insert(bookmark)
        }
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        withContext(Dispatchers.IO){
            bookmarkDao.deleteBookmark(bookmark)
        }
    }

    override fun getAllBookmark(): Flow<List<Bookmark>> {
      return  bookmarkDao.allBookmark().flowOn(Dispatchers.IO)
    }
}





