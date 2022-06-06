package com.example.reviewapp.domain.repository

import com.example.reviewapp.domain.model.News

interface NewsRepository {

    suspend fun getNews(
        onSuccess: (location: News) -> Unit,
        onFailed: (message: String) -> Unit
    )
}