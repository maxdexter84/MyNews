package ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.maxdexter.mynews.domain.repository.INewsRepository
import ru.maxdexter.mynews.ui.entity.UIModel
import ru.maxdexter.mynews.ui.paging.BreakingNewsPagingSource
import ru.maxdexter.mynews.domain.common.Constants

class BreakingNewsViewModel (private val repository: INewsRepository): ViewModel() {
    private var currentCountryCode: String? = null
    private var currentCategory: String? = null
    private var currentSearchResult: Flow<PagingData<UIModel>>? = null


    init {
        getBreakingNews("ru","general")
    }

     fun getBreakingNews(countryCode:String, category: String): Flow<PagingData<UIModel>>{
        val lastResult = currentSearchResult
        if (lastResult != null && currentCategory == category && currentCountryCode == countryCode){
            return lastResult
        }
        currentCategory = category
        currentCountryCode = countryCode
        val newResult = pagingBreakingNews(countryCode,category).cachedIn(viewModelScope) //cachedIn кеширует данные из результатов запроса
        currentSearchResult = newResult
        return newResult
    }
    
    private fun pagingBreakingNews(countryCode: String, category: String): Flow<PagingData<UIModel>> {
        return Pager(config = PagingConfig(pageSize = Constants.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { BreakingNewsPagingSource(repository,category,countryCode,) }).flow
    }
    

}