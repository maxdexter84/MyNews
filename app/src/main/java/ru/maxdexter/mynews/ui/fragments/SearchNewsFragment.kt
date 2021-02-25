package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.NewsAdapter
import ru.maxdexter.mynews.databinding.FragmentSearchNewsBinding
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel.SearchNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel.SearchNewsViewModelFactory

class SearchNewsFragment: Fragment() {
    private val repository: NewsRepository by lazy{
        NewsRepository(ArticleDatabase.invoke(requireContext()).getArticleDao(), RetrofitInstance.api)
    }
    private val viewModel: SearchNewsViewModel by lazy {
        ViewModelProvider(this, SearchNewsViewModelFactory(repository)).get(SearchNewsViewModel::class.java)
    }
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSearchNewsBinding
    private var searchJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_news,container, false)
        setRecyclerView()
        initSearch()

        return binding.root
    }

    private fun search(query: String, countryCode: String) {
        searchJob?.cancel()
        hideProgressBar()
        searchJob = lifecycleScope.launch {
            viewModel.getSearchingNews(query,getString(R.string.country_code)).collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        val recycler = binding.rvSearchNews
        recycler.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initSearch() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        // Прокрутит список вверх, когда он будет обновлен из сети
        lifecycleScope.launch {
            newsAdapter.loadStateFlow
                // Излучает только при изменении состояния загрузки.
                .distinctUntilChangedBy { it.refresh }
                // Реагируйте только на те случаи, когда удаленное ОБНОВЛЕНИЕ завершается, то есть не загружается.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvSearchNews.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        showProgressBar()
        binding.etSearch.text.trim().let {
            if (it.isNotEmpty()) {
                binding.rvSearchNews.scrollToPosition(0)
                search(it.toString(),requireContext().resources.getString(R.string.country_code))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        searchJob?.cancel()
    }

}