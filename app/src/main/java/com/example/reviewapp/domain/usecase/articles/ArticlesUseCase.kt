package com.example.reviewapp.domain.usecase.articles

data class ArticlesUseCase (
    val addArticles: AddArticles,
    val getArticles: GetArticles,
    val deleteAllArticles: DeleteAllArticles,
    val getArticlePage: GetArticlePage
)