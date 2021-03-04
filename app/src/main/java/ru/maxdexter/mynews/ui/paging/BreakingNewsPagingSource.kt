package ru.maxdexter.mynews.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import ru.maxdexter.mynews.domain.models.Resource
import ru.maxdexter.mynews.domain.repository.INewsRepository
import ru.maxdexter.mynews.ui.entity.UIModel
import ru.maxdexter.mynews.util.Constants.Companion.PAGE_SIZE
import ru.maxdexter.mynews.util.Constants.Companion.START_PAGE
import java.io.IOException

class BreakingNewsPagingSource(private val repository: INewsRepository,
                               private val category: String,
                               private val countryCode: String): PagingSource<Int, UIModel>() {
    override fun getRefreshKey(state: PagingState<Int, UIModel>): Int? {
        return null
    }
    @Suppress("UNCHECKED_CAST")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UIModel> {
        val position = params.key ?: START_PAGE
        return try {
            var repos = emptyList<UIModel>()
            repository.loadBreakingNews(
                pageNumber = position,
                category = category,
                pageSize = params.loadSize,
                countryCod = countryCode
            ).collect { 
                when(it){
                    is Resource.Success<*> -> repos = it.data as List<UIModel>
                    is Resource.Error -> Log.i("APP_INFO",it.message)
                    else -> Log.i("APP_INFO","loading data")
                }
            }
            
            val nextKey = if (repos.isEmpty()) null else position + (params.loadSize / PAGE_SIZE)
            LoadResult.Page(data = repos, prevKey = if (position == START_PAGE) null else position - 1, nextKey = nextKey)
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}