package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.databinding.FragmentArticleBinding
import ru.maxdexter.mynews.data.db.AppDatabase
import ru.maxdexter.mynews.domain.repository.ILocalSource
import ru.maxdexter.mynews.domain.repository.IRemoteSource
import ru.maxdexter.mynews.data.repository.LocalSourceImpl
import ru.maxdexter.mynews.data.repository.NewsRepository
import ru.maxdexter.mynews.data.repository.RemoteSourceImpl
import ru.maxdexter.mynews.ui.viewmodels.articleviewmodel.ArticleViewModel
import ru.maxdexter.mynews.ui.viewmodels.articleviewmodel.ArticleViewModelFactory

class ArticleFragment : Fragment() {


   private lateinit var binding: FragmentArticleBinding
   private lateinit var viewModel: ArticleViewModel
   private lateinit var viewModelFactory: ArticleViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_article,container, false)
        val args = arguments?.let { ArticleFragmentArgs.fromBundle(it) }
        val uiModel = args?.uiModel
        val localSourceImpl: ILocalSource = LocalSourceImpl(AppDatabase.invoke(requireContext()).bookmarkDao())
        val remoteSourceImpl: IRemoteSource = RemoteSourceImpl(RetrofitInstance.api)
        val repository = NewsRepository(localSourceImpl,remoteSourceImpl )
        initViewModel(repository)
        initWebView(args)


        binding.fab.setOnClickListener {
           if (uiModel != null)
            viewModel.saveArticle(uiModel)
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
            webView.loadUrl(args.uiModel.url)
        }
    }
}