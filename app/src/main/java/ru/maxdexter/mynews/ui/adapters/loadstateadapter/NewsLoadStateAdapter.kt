package ru.maxdexter.mynews.ui.adapters.loadstateadapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class NewsLoadStateAdapter(private val retry:()->Unit): LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.from(parent,retry)
    }
}