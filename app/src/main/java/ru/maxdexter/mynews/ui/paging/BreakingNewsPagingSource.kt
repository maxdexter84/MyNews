package ru.maxdexter.mynews.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.maxdexter.mynews.data.api.NewsAPI
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.util.Constants.Companion.PAGE_SIZE
import ru.maxdexter.mynews.util.Constants.Companion.START_PAGE
import java.io.IOException
import java.lang.Exception

class BreakingNewsPagingSource(private val api:NewsAPI,
                               private val category: String,
                               private val countryCode: String): PagingSource<Int,Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: START_PAGE
        return try {
            val response = api.getBreakingNews(
                pageNumber = position,
                category = category,
                pageSize = params.loadSize,
                countryCod = countryCode
            )
            val repos = response.articles
            val nextKey = if (repos.isEmpty()) null else position + (params.loadSize / PAGE_SIZE)
            LoadResult.Page(data = repos, prevKey = if (position == START_PAGE) null else position - 1, nextKey = nextKey)
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}