package com.example.reviewapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.reviewapp.domain.model.Articles
import kotlinx.coroutines.flow.Flow


interface ArticlesRepository {

    suspend fun getArticles(): List<Articles>

    suspend fun insertArticles(articles: Articles)

    suspend fun delete(articles: Articles)

    suspend fun deleteAll()

    fun getArticlePage(): LiveData<PagingData<Articles>>
}