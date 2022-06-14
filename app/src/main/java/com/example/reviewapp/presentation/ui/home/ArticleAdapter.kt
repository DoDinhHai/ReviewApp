package com.example.reviewapp.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reviewapp.databinding.NewsItemBinding
import com.example.reviewapp.domain.model.Articles


class ArticleAdapter( val onClick: (Articles) -> Unit) :
    PagingDataAdapter<Articles,ArticleAdapter.NewsViewHolder>(COMPARATOR) {

    inner class NewsViewHolder(private val newsItemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsItemBinding.root) {

        fun onBind(articles: Articles?) {
            Glide.with(itemView).load(articles?.urlToImage).into(newsItemBinding.imgNews);
            newsItemBinding.txtNewsName.text = articles?.title
            newsItemBinding.txtNewsDescription.text = articles?.description
            newsItemBinding.itemArticle.setOnClickListener {
                if (articles != null) {
                    onClick(articles)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding =
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean =
                oldItem == newItem
        }
    }
}