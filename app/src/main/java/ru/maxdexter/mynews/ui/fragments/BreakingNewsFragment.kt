package ru.maxdexter.mynews.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.Resource
import ru.maxdexter.mynews.adapters.NewsAdapter
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.fragments.BreakingNewsFragmentDirections.Companion.actionBreakingNewsFragmentToArticleFragment
import ru.maxdexter.mynews.ui.viewmodels.BreakingNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.BreakingNewsViewModelFactory

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: BreakingNewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        val viewModelFactory = BreakingNewsViewModelFactory(repository )
        viewModel = ViewModelProvider(this, viewModelFactory).get(BreakingNewsViewModel::class.java)


        setRecyclerView()
        initObserveData(view)
        newsAdapter.setOnClickListener {
            findNavController().navigate(actionBreakingNewsFragmentToArticleFragment(it))
        }

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
        paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }


    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}