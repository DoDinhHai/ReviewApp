package com.example.reviewapp.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reviewapp.databinding.CategoryItemBinding
import com.example.reviewapp.databinding.NewsItemBinding
import com.example.reviewapp.domain.model.Articles

class CategoryAdapter(var listNews: List<Articles>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(private val categoryItemBinding: CategoryItemBinding): RecyclerView.ViewHolder(categoryItemBinding.root){

        fun bind(articles: Articles){
            Glide.with(itemView).load(articles.urlToImage).into(categoryItemBinding.categoryImg);
            categoryItemBinding.categoryName.text = articles.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = listNews[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    fun setData(list: List<Articles>){
        listNews = list
        notifyDataSetChanged()
    }
}