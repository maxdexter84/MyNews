package ru.maxdexter.mynews.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.models.Resource
import ru.maxdexter.mynews.ui.adapters.NewsAdapter
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.databinding.FragmentBreakingNewsBinding
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel.BreakingNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel.BreakingNewsViewModelFactory

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel: BreakingNewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentBreakingNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news,container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()).getArticleDao(), RetrofitInstance.api)
        val viewModelFactory = BreakingNewsViewModelFactory(repository )
        viewModel = ViewModelProvider(this, viewModelFactory).get(BreakingNewsViewModel::class.java)
        setRecyclerView()
        initObserveData(view)
    }

    private fun initObserveData(view: View) {
        viewModel.breakingNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    newsAdapter.differ.submitList(response.data?.articles)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Log.e("ERROR", "${response.message}")
                    Snackbar.make(view, "Ошибка загрузки данных!!", Snackbar.LENGTH_LONG).show()
                }

                is Resource.Loading -> showProgressBar()
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}