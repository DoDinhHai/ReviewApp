package com.example.reviewapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.data.repository.NewsRepositoryImpl
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepositoryImpl: NewsRepositoryImpl) :
    ViewModel() {

    private val _listNews = MutableLiveData<List<Articles>>()
    val listNews: LiveData<List<Articles>> get() = _listNews

    private val _requestFail = MutableLiveData<String>()
    val requestFail: LiveData<String> get() = _requestFail

    private val _isShowLoading = MutableLiveData(false)
    val isShowLoading: LiveData<Boolean> get() = _isShowLoading

    fun getNews() {
        Log.d("AAAAAAAA","getNews")
        viewModelScope.launch {
            showLoading(true)
            newsRepositoryImpl.getNews(
                onSuccess = {
                    _listNews.postValue(it.articles)
                            },
                onFailed = {
                    _requestFail.postValue(it)
                    showLoading(false)
                })
        }
    }

    private fun showLoading(isShow: Boolean) {
        _isShowLoading.postValue(isShow)
    }

    fun getListNews() = _listNews
}