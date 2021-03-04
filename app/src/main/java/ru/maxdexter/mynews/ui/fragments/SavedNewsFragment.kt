package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.newsadapter.NewsAdapter
import ru.maxdexter.mynews.databinding.FragmentSavedNewsBinding
import ru.maxdexter.mynews.data.db.AppDatabase
import ru.maxdexter.mynews.domain.mappers.UIModelBookmark
import ru.maxdexter.mynews.domain.repository.ILocalSource
import ru.maxdexter.mynews.domain.repository.IRemoteSource
import ru.maxdexter.mynews.data.repository.LocalSourceImpl
import ru.maxdexter.mynews.data.repository.NewsRepository
import ru.maxdexter.mynews.data.repository.RemoteSourceImpl
import ru.maxdexter.mynews.ui.adapters.loadstateadapter.NewsLoadStateAdapter
import ru.maxdexter.mynews.ui.viewmodels.savednewsviewmodel.SavedNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.savednewsviewmodel.SavedNewsViewModelFactory

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var binding: FragmentSavedNewsBinding
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_news, container, false)
        val localSourceImpl: ILocalSource = LocalSourceImpl(AppDatabase.invoke(requireContext()).bookmarkDao())
        val remoteSourceImpl: IRemoteSource = RemoteSourceImpl(RetrofitInstance.api)
        val repository = NewsRepository(localSourceImpl,remoteSourceImpl )
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedNewsViewModel::class.java)

        initRecyclerView()
        observeData()
        initItemTouchHelper()
        return binding.root
    }

    private fun initItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback())
        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews)
    }

    private fun observeData() {
        viewModel.savedBookmarks.observe(viewLifecycleOwner, {
            if (it != null) {
                lifecycleScope.launch {
                    newsAdapter.submitData(PagingData.from(it))
                }

            }
        })
    }

    private fun initRecyclerView() {
        binding.rvSavedNews .apply {
            adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { newsAdapter.retry() },
                footer = NewsLoadStateAdapter { newsAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val uiModel = newsAdapter.peek(position)
                if (uiModel != null) {
                    viewModel.deleteBookmark(UIModelBookmark().fromUIModelToUIBookmark(uiModel))
                }
                Snackbar.make(binding.root,"Отменить удаление?", Snackbar.LENGTH_LONG).setAction("Да") {
                    if (uiModel != null) {
                        viewModel.saveBookmark(UIModelBookmark().fromUIModelToUIBookmark(uiModel))
                    }
                }.show()
            }
        }
    }

}