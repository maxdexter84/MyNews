package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.FragmentArticleBinding
import ru.maxdexter.mynews.models.Article

class ArticleFragment : Fragment() {


    lateinit var binding: FragmentArticleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_article,container, false)
        val args = arguments?.let { ArticleFragmentArgs.fromBundle(it) }
        val article = args?.article


      val webView =  binding.webView
        webView.webViewClient = WebViewClient()
        if (args != null) {
            webView.loadUrl(args.article.url)
        }

        return binding.root
    }
}