package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.SearchNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.SearchNewsViewModelFactory

class SearchNewsFragment: BottomSheetDialogFragment() {

    lateinit var viewModel: SearchNewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        val viewModelFactory = SearchNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchNewsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search_news,container, false)
    }
}