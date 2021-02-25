package ru.maxdexter.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.data.api.RetrofitInstance
import ru.maxdexter.mynews.ui.adapters.NewsAdapter
import ru.maxdexter.mynews.databinding.FragmentSavedNewsBinding
import ru.maxdexter.mynews.data.db.ArticleDatabase
import ru.maxdexter.mynews.repository.NewsRepository
import ru.maxdexter.mynews.ui.viewmodels.savednewsviewmodel.SavedNewsViewModel
import ru.maxdexter.mynews.ui.viewmodels.savednewsviewmodel.SavedNewsViewModelFactory

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_news, container, false)
        val repository = NewsRepository(ArticleDatabase.invoke(requireContext()).getArticleDao(), RetrofitInstance.api)
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedNewsViewModel::class.java)

        initRecycler()
        observeData()
        initItemTouchHelper()
        return binding.root
    }

    private fun initItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback())
        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews)
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
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(binding.root,"Отменить удаление?", Snackbar.LENGTH_LONG).setAction("Да") {
                    viewModel.saveArticle(article)
                }.show()
            }
        }
    }

}