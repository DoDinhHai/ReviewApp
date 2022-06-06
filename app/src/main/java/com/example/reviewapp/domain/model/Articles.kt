package com.example.reviewapp.domain.model

data class Articles(
    val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String
): Model()
