package ru.maxdexter.mynews.ui.adapters.newsadapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.maxdexter.mynews.data.entity.api.Article
import ru.maxdexter.mynews.ui.entity.UIModel


class NewsAdapter : PagingDataAdapter<UIModel, NewsViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
       return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val uiModel = getItem(position)
        if (uiModel != null) {
            holder.bind(uiModel)
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<UIModel>(){
            override fun areItemsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
                return  oldItem == newItem
            }
        }
    }
}