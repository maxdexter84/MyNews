package ru.maxdexter.mynews.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingSource
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.NewsAdapter
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.databinding.FragmentBreakingNewsBinding
import ru.maxdexter.mynews.repository.NewsRepository
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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        showProgressBar()
        initObserveData(view)

    }

    private fun initObserveData(view: View) {
        lifecycleScope.launch {
            viewModel.getBreakingNews(getString(R.string.country_code),getString(R.string.news_category)).collect {
                when (it){
                    is PagingSource.LoadResult.Page<*,*> -> {
                        hideProgressBar()
                        newsAdapter.submitData(it)
                    }
                    is PagingSource.LoadResult.Error<*,*> -> {
                        hideProgressBar()
                        Snackbar.make(view,getString(R.string.Load_result_error_text),Snackbar.LENGTH_SHORT).show()
                    }
                }
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
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}