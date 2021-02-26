package ru.maxdexter.mynews.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.maxdexter.mynews.data.api.NewsAPI
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.util.Constants
import ru.maxdexter.mynews.util.Constants.Companion.START_PAGE
import java.io.IOException

class SearchNewsPagingSource(
    private val newsAPI: NewsAPI,
    private val countryCode: String,
    private val query: String) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: START_PAGE
        return try {
            val response = newsAPI.searchingNews(
                pageNumber = position,
                pageSize = params.loadSize,
                searchQuery = query,
                countryCod = countryCode
            )
            val repos = response.articles
            val nextKey = if (repos.isEmpty()) null else position + (params.loadSize / Constants.PAGE_SIZE)
            LoadResult.Page(data = repos, prevKey = if (position == START_PAGE) null else position - 1, nextKey = nextKey)
        }catch (e: IOException){LoadResult.Error(e)}
    }
}