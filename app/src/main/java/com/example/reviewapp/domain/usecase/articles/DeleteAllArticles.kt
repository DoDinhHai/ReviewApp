package com.example.reviewapp.domain.usecase.articles

import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.repository.ArticlesRepository

class DeleteAllArticles(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(){
        articlesRepository.deleteAll()
    }
}