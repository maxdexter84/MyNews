package ru.maxdexter.mynews.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.ItemArticlePreviewBinding
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.ui.fragments.BreakingNewsFragmentDirections
import ru.maxdexter.mynews.ui.fragments.SavedNewsFragmentDirections
import ru.maxdexter.mynews.ui.fragments.SearchNewsFragmentDirections

class ArticleViewHolder(val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article){
        binding.article = article
        binding.executePendingBindings()
        binding.setClickListener {
            when(it.findNavController().currentDestination?.id){
                R.id.breakingNewsFragment -> it.findNavController().navigate( BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article))
                R.id.savedNewsFragment -> it.findNavController().navigate( SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(article))
                R.id.searchNewsFragment -> it.findNavController().navigate( SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article))
            }
        }
    }

    companion object{
        fun from(parent: ViewGroup):ArticleViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            return  ArticleViewHolder(ItemArticlePreviewBinding.inflate(layoutInflater,parent,false))
        }
    }
}