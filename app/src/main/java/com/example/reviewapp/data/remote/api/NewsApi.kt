package com.example.reviewapp.data.remote.api

import com.example.reviewapp.domain.model.News
import retrofit2.Response
import retrofit2.http.*

interface NewsApi {

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Response<News>
}