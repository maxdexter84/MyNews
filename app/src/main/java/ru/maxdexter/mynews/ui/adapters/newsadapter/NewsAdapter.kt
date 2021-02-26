package ru.maxdexter.mynews.ui.adapters.newsadapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.maxdexter.mynews.models.Article


class NewsAdapter : PagingDataAdapter<Article, ArticleViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
       return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return  oldItem == newItem
            }
        }
    }
}