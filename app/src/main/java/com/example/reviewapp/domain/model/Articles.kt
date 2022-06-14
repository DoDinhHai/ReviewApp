package com.example.reviewapp.domain.model

data class Articles(
    var id: Int,
    var author: String,
    val title: String,
    var description: String,
    val url: String,
    var urlToImage: String,
    val content: String
): Model()
