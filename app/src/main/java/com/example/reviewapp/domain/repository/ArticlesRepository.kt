package com.example.reviewapp.domain.repository

import com.example.reviewapp.data.model.ArticlesEntity
import com.example.reviewapp.domain.model.Articles


interface ArticlesRepository {

    suspend fun getArticles(): List<Articles>

    suspend fun insertArticles(articles: ArticlesEntity)

    suspend fun delete(articles: ArticlesEntity)
}