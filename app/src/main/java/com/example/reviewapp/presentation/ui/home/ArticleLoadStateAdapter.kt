package com.example.reviewapp.presentation.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reviewapp.databinding.ArticleLoadStateViewItemBinding

class ArticleLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ArticleLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        private val itemBinding: ArticleLoadStateViewItemBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.btnRetry.setOnClickListener {
                retry.invoke()
            }
            Log.d("Paging","progressBar")
        }

        fun onBind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                itemBinding.txtError.text = loadState.error.localizedMessage
            }
            itemBinding.progressBar.isVisible = true
            itemBinding.btnRetry.isVisible = loadState !is LoadState.Loading
            itemBinding.txtError.isVisible = loadState !is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val itemBinding = ArticleLoadStateViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(itemBinding, retry)
    }
}