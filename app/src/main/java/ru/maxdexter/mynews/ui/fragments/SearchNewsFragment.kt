package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.models.Resource
import ru.maxdexter.mynews.ui.adapters.NewsAdapter
import ru.maxdexter.mynews.databinding.FragmentSearchNewsBinding
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel.SearchNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.seachnewsviewmodel.SearchNewsViewModelFactory

class SearchNewsFragment: BottomSheetDialogFragment() {

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
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        val viewModelFactory = SearchNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchNewsViewModel::class.java)
        val view = binding.root

        setRecyclerView(view)
        initObserveData(view)
        queryListener()

        newsAdapter.setOnClickListener {
            findNavController().navigate(
                SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(
                    it
                )
            )
        }

        return view
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
        scope.launch {
            delay(500)
            if (s.length > 2)
                viewModel.getSearchingNews(s.toString())
        }
    }


    private fun initObserveData(view: View) {
        viewModel.searchNews.observe(viewLifecycleOwner, { response ->
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


    private fun setRecyclerView(view: View) {
        newsAdapter = NewsAdapter()
        val recycler = view.findViewById<RecyclerView>(R.id.rvSearchNews)
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