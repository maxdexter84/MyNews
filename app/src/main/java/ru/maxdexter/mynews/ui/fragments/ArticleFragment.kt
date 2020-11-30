package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.FragmentArticleBinding
import ru.maxdexter.mynews.db.ArticleDatabase
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.models.NewsResponse
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.ArticleViewModel
import ru.maxdexter.mynews.ui.viewmodels.ArticleViewModelFactory

class ArticleFragment : Fragment() {


    lateinit var binding: FragmentArticleBinding
    lateinit var viewModel: ArticleViewModel
    lateinit var viewModelFactory: ArticleViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_article,container, false)
        val args = arguments?.let { ArticleFragmentArgs.fromBundle(it) }
        val article = args?.article
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()))
        initViewModel(repository)
        initWebView(args)


        binding.fab.setOnClickListener {
           if (article != null)
            viewModel.saveArticle(article)
        }

        return binding.root
    }

    private fun initViewModel(repository: NewsRepository) {
        viewModelFactory = ArticleViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArticleViewModel::class.java)
    }

    private fun initWebView(args: ArticleFragmentArgs?) {
        val webView = binding.webView
        webView.webViewClient = WebViewClient()
        if (args != null) {
            webView.loadUrl(args.article.url)
        }
    }
}