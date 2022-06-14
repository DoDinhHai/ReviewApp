package com.example.reviewapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.reviewapp.data.local.db.dao.ArticlesDao
import com.example.reviewapp.data.model.ArticlesEntity
import com.example.reviewapp.data.model.ArticlesEntityMapper
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val dao: ArticlesDao, private val articlesEntityMapper: ArticlesEntityMapper) : ArticlesRepository {
    override suspend fun getArticles(): List<Articles> {
        return dao.getAll()
    }

    override suspend fun insertArticles(articles: Articles) {
        dao.insert(articlesEntityMapper.mapToEntity(articles))
    }

    override suspend fun delete(articles: Articles) {
        dao.delete(articlesEntityMapper.mapToEntity(articles))
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun getArticlePage(): Flow<PagingData<Articles>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { ArticlePagingResource(dao)}
    ).flow
}