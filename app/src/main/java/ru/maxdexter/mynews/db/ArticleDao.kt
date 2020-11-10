package ru.maxdexter.mynews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.maxdexter.mynews.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article): Int


}