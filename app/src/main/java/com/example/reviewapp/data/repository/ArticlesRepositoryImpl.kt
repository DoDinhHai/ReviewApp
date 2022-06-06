package com.example.reviewapp.data.repository

import com.example.reviewapp.data.local.db.dao.ArticlesDao
import com.example.reviewapp.data.model.ArticlesEntity
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.repository.ArticlesRepository
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val dao: ArticlesDao) : ArticlesRepository {
    override suspend fun getArticles(): List<Articles> {
        return dao.getAll()
    }

    override suspend fun insertArticles(articles: ArticlesEntity) {
        dao.insert(articles)
    }

    override suspend fun delete(articles: ArticlesEntity) {
        dao.delete(articles)
    }
}