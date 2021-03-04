package ru.maxdexter.mynews.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.newsadapter.NewsAdapter
import ru.maxdexter.mynews.data.db.AppDatabase
import ru.maxdexter.mynews.databinding.FragmentBreakingNewsBinding
import ru.maxdexter.mynews.domain.repository.ILocalSource
import ru.maxdexter.mynews.domain.repository.IRemoteSource
import ru.maxdexter.mynews.data.repository.LocalSourceImpl
import ru.maxdexter.mynews.data.repository.NewsRepository
import ru.maxdexter.mynews.data.repository.RemoteSourceImpl
import ru.maxdexter.mynews.ui.adapters.loadstateadapter.NewsLoadStateAdapter
import ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel.BreakingNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.breakingnewsviewmodel.BreakingNewsViewModelFactory
import ru.maxdexter.mynews.ui.util.extensions.loadStateListener

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    private val repository: NewsRepository by lazy {
        val localSourceImpl: ILocalSource = LocalSourceImpl(AppDatabase.invoke(requireContext()).bookmarkDao())
        val remoteSourceImpl: IRemoteSource = RemoteSourceImpl(RetrofitInstance.api)
        NewsRepository(localSourceImpl, remoteSourceImpl)
    }
    private val viewModel: BreakingNewsViewModel by lazy {
        ViewModelProvider(this, BreakingNewsViewModelFactory(repository )).get(BreakingNewsViewModel::class.java)
    }
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }


    private lateinit var binding: FragmentBreakingNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_news,container,false)

        initRecyclerView()

        arguments?.let { BreakingNewsFragmentArgs.fromBundle(it).category }?.let { initObserveData(it) }

        return binding.root
    }

    private fun initObserveData(category: String) {
        lifecycleScope.launch {
            viewModel.getBreakingNews(getString(R.string.country_code),category).collect {
                newsAdapter.submitData(it)
            }
        }

    }

    private fun initRecyclerView() {
        binding.rvBreakingNews.apply {
            adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { newsAdapter.retry() },
                footer = NewsLoadStateAdapter { newsAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
        newsAdapter.loadStateListener(binding,requireContext())
    }




}