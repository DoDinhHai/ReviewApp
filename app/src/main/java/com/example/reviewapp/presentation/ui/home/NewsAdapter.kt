package com.example.reviewapp.presentation.ui.home

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reviewapp.R
import com.example.reviewapp.databinding.MenuItemBinding
import com.example.reviewapp.databinding.NewsItemBinding
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.model.News
import java.net.URI
import java.net.URL


class NewsAdapter( var listNews: List<Articles>):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(private val newsItemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsItemBinding.root) {
        fun bind(articles: Articles) {
            var uri = Uri.parse("https://vouchers.com.vn/media/uploads/froala_editor/images/image10_LTh5UzL.jpg");
            //newsItemBinding.imgNews.setImageURI(uri)
            //Glide.with(itemView).load("https://vouchers.com.vn/media/uploads/froala_editor/images/image10_LTh5UzL.jpg").into(newsItemBinding.imgNews);
            Glide.with(itemView).load(articles.urlToImage).into(newsItemBinding.imgNews);
            newsItemBinding.txtNewsName.text = articles.title
            newsItemBinding.txtNewsDescription.text = articles.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)
    }

    fun setData(list: List<Articles>){
        listNews = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = listNews[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }
}