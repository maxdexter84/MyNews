package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.SavedNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.SavedNewsViewModelFactory

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: SavedNewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedNewsViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}