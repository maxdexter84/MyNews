package ru.maxdexter.mynews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import ru.maxdexter.mynews.NavigationDirections
import ru.maxdexter.mynews.R
import ru.maxdexter.mynews.databinding.ItemArticlePreviewBinding
import ru.maxdexter.mynews.models.Article
import ru.maxdexter.mynews.ui.fragments.BreakingNewsFragmentDirections

class NewsAdapter : PagingDataAdapter<Article,NewsAdapter.ArticleViewHolder>(diffCallback) {

   class ArticleViewHolder(val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article){
                binding.executePendingBindings()
                Glide.with(binding.ivArticleImage).load(article.urlToImage).into(binding.ivArticleImage)
                binding.tvSource.text = article.source.name
                binding.tvTitle.text = article.title
                binding.tvDescription.text = article.description
                binding.tvPublishedAt.text = article.publishedAt

                binding.setClickListener {
                    it.findNavController().navigate( BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article))
                }

            }

      companion object{
          fun from(parent: ViewGroup):ArticleViewHolder{
              val layoutInflater = LayoutInflater.from(parent.context)
              return  ArticleViewHolder(ItemArticlePreviewBinding.inflate(layoutInflater,parent,false))
          }
      }

    }

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