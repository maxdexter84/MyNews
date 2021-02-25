package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingSource
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.NewsAdapter
import ru.maxdexter.mynews.databinding.FragmentSearchNewsBinding
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel.SearchNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel.SearchNewsViewModelFactory

class SearchNewsFragment: Fragment() {

    private lateinit var viewModel: SearchNewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSearchNewsBinding
    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_news,container, false)
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()).getArticleDao(), RetrofitInstance.api)
        val viewModelFactory = SearchNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchNewsViewModel::class.java)

        showProgressBar()
        setRecyclerView()
        queryListener()
        return binding.root
    }

    private fun queryListener() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

            override fun afterTextChanged(s: Editable?) {
               s?.let { searchDelay(it) }
            }
        })
    }

    fun searchDelay(s: Editable) {
        if (s.length > 2)
            initObserveData(s.toString(),requireContext().resources.getString(R.string.country_code))
            viewModel.getSearchingNews(s.toString(),"ru")

    }


    private fun initObserveData(query: String, countryCode: String) {
        hideProgressBar()
        lifecycleScope.launch {
            viewModel.getSearchingNews(query,getString(R.string.country_code)).collect {
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

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

}