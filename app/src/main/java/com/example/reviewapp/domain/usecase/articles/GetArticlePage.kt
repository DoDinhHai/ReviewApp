package com.example.reviewapp.domain.usecase.articles

import androidx.paging.PagingData
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow

class GetArticlePage(private val articlesRepository: ArticlesRepository) {

    operator fun invoke(): Flow<PagingData<Articles>> {
        return articlesRepository.getArticlePage()
    }
}