package ru.maxdexter.mynews.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.BreakingNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.BreakingNewsViewModelFactory

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: BreakingNewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        val viewModelFactory = BreakingNewsViewModelFactory(repository )
        viewModel = ViewModelProvider(this, viewModelFactory).get(BreakingNewsViewModel::class.java)

    }


}