package ru.maxdexter.mynews.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.data.entity.db.Bookmark


@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark): Long

    @Query("SELECT * FROM bookmark")
    fun allBookmark(): Flow<List<Bookmark>>

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)


}