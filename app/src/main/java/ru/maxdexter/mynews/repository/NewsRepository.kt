package ru.maxdexter.mynews.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.maxdexter.mynews.data.api.NewsAPI
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.data.db.ArticleDao
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.ui.paging.BreakingNewsPagingSource
import ru.maxdexter.mynews.ui.paging.SearchNewsPagingSource
import ru.maxdexter.mynews.util.Constants.Companion.PAGE_SIZE

class NewsRepository(private val articleDao: ArticleDao, private val newsAPI: NewsAPI) : INewsRepository {

    override  fun getBreakingNews(countryCode: String, category: String): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {BreakingNewsPagingSource(newsAPI,category,countryCode,)}).flow.flowOn(Dispatchers.IO)
    }

    override  fun searchNews(query: String, countryCode: String): Flow<PagingData<Article>>{
       return Pager(config = PagingConfig(pageSize = PAGE_SIZE,enablePlaceholders = false),
            pagingSourceFactory = {SearchNewsPagingSource(newsAPI,countryCode,query)}).flow.flowOn(Dispatchers.IO)
    }

    override suspend fun saveArticle(article: Article) {
        withContext(Dispatchers.IO){
            articleDao.insert(article)
        }
    }

    override suspend fun deleteArticle(article: Article) {
        withContext(Dispatchers.IO){
            articleDao.deleteArticle(article)
        }
    }

    override fun getSavedArticle() = articleDao.getAllArticles()


}