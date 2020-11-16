package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.adapters.NewsAdapter
import ru.maxdexter.mynews.databinding.FragmentSavedNewsBinding
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.SavedNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.SavedNewsViewModelFactory

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: SavedNewsViewModel
    lateinit var binding: FragmentSavedNewsBinding
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_news, container, false)
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedNewsViewModel::class.java)

        initRecycler()
        observeData()
        newsAdapter.setOnClickListener {
            findNavController().navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it))
        }

        return binding.root
    }

    private fun observeData() {
        viewModel.savedArticle.observe(viewLifecycleOwner, {
            if (it != null) {
                newsAdapter.differ.submitList(it)
            }
        })
    }

    private fun initRecycler() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}