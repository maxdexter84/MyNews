package ru.maxdexter.mynews.ui.adapters.newsadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.ItemArticlePreviewBinding
import ru.maxdexter.mynews.data.entity.api.Article
import ru.maxdexter.mynews.ui.entity.UIModel
import ru.maxdexter.mynews.ui.fragments.BreakingNewsFragmentDirections
import ru.maxdexter.mynews.ui.fragments.SavedNewsFragmentDirections
import ru.maxdexter.mynews.ui.fragments.SearchNewsFragmentDirections

class NewsViewHolder(val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(uiModel: UIModel){
        binding.uiModel = uiModel
        binding.executePendingBindings()
        binding.setClickListener {
            when(it.findNavController().currentDestination?.id){
                R.id.breakingNewsFragment -> it.findNavController().navigate( BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(uiModel))
                R.id.savedNewsFragment -> it.findNavController().navigate( SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(uiModel))
                R.id.searchNewsFragment -> it.findNavController().navigate( SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(uiModel))
            }
        }
    }

    companion object{
        fun from(parent: ViewGroup): NewsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemArticlePreviewBinding.inflate(layoutInflater,parent,false)
            return  NewsViewHolder(binding)
        }
    }
}