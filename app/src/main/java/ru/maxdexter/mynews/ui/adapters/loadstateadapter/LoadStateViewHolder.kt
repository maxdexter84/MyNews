package ru.maxdexter.mynews.ui.adapters.loadstateadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.maxdexter.mynews.databinding.LoadStateViewItemBinding

class LoadStateViewHolder(
    private val binding: LoadStateViewItemBinding,
    retry:()->Unit): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }
    fun bind(loadState: LoadState){
        if (loadState is LoadState.Error){
            binding.tvErrorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState !is LoadState.Loading
        binding.tvErrorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object{
        fun from(parent: ViewGroup,retry: () -> Unit): LoadStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LoadStateViewItemBinding.inflate(layoutInflater)
            return LoadStateViewHolder(binding,retry)
        }
    }

}