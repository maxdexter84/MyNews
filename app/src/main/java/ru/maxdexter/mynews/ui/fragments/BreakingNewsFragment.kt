package ru.maxdexter.mynews.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.newsadapter.NewsAdapter
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.databinding.FragmentBreakingNewsBinding
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.adapters.loadstateadapter.NewsLoadStateAdapter
import ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel.BreakingNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel.BreakingNewsViewModelFactory

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    private val repository: NewsRepository by lazy {
        NewsRepository(ArticleDatabase.invoke(requireContext()).getArticleDao(), RetrofitInstance.api)
    }
    private val viewModel: BreakingNewsViewModel by lazy {
        ViewModelProvider(this, BreakingNewsViewModelFactory(repository )).get(BreakingNewsViewModel::class.java)
    }
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    private lateinit var binding: FragmentBreakingNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news,container,false)

        setRecyclerView()
        initObserveData()
        return binding.root
    }


    private fun initObserveData() {
        showProgressBar()
        lifecycleScope.launch {
            viewModel.getBreakingNews(getString(R.string.country_code),getString(R.string.news_category)).collect {
                hideProgressBar()
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
        binding.rvBreakingNews.apply {
            adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { newsAdapter.retry() },
                footer = NewsLoadStateAdapter { newsAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}