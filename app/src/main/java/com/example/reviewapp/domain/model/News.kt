package com.example.reviewapp.domain.model

data class News(
    val id: Int,
    val status: String,
    val articles: List<Articles>
)
