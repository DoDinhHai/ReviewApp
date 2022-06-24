package com.example.reviewapp.data.repository

import android.util.Log
import com.example.reviewapp.data.remote.api.NewsApi
import com.example.reviewapp.domain.model.News
import com.example.reviewapp.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi  ): NewsRepository {

    override suspend fun getNews(
        onSuccess: (news: News) -> Unit,
        onFailed: (message: String) -> Unit
    ) = withContext(Dispatchers.IO) {
        //https://newsapi.org/v2/everything?q=tesla&from=2022-04-26&sortBy=publishedAt&apiKey=6ea78d2b5e8f457fa74a45ca1cac3240
        try {
            val response = newsApi.getAllNews("tesla","2022-05-22","publishedAt","6ea78d2b5e8f457fa74a45ca1cac3240")
            when (response.isSuccessful) {
                true -> {
                    val listNews = response.body()
                    if (listNews != null) {
                        onSuccess(listNews)
                    }
                }
                false -> {
                    Log.e("RetrofitCallFailed", response.message())
                    onFailed(response.message() ?: "Get data fail, please try again")
                }
            }
        } catch(ex: Exception) {
            if(ex is UnknownHostException) {
                onFailed("No network connection, please check your net work.")
            } else {
                onFailed("Can't connect to server, please try again later")
            }
        }
    }
}