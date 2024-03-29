package com.example.reviewapp.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.reviewapp.data.repository.NewsRepositoryImpl
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.usecase.articles.ArticlesUseCase
import com.example.reviewapp.domain.usecase.menu.MenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val menuUseCase: MenuUseCase,
    private val articlesUseCase: ArticlesUseCase,
    private val newsRepositoryImpl: NewsRepositoryImpl
) : ViewModel() {


    private var oldFragment : String? = null
    private val _listMenu = MutableLiveData<List<Menu>>()
    val listMenu: LiveData<List<Menu>> get() = _listMenu

    private val _listNews = MutableLiveData<List<Articles>>()
    val listNews: LiveData<List<Articles>> get() = _listNews

    private val _listArticles = MutableLiveData<PagingData<Articles>>()
    val listArticles: LiveData<PagingData<Articles>> get() = _listArticles


    private val _articles = MutableLiveData<Articles>()
    val articles: LiveData<Articles> get() = _articles
    private val _requestFail = MutableLiveData<String>()
    val requestFail: LiveData<String> get() = _requestFail

    private val _isShowLoading = MutableLiveData(false)
    val isShowLoading: LiveData<Boolean> get() = _isShowLoading

    init {
        getNews()
    }

    fun getMenu() {
        menuUseCase.getMenu.invoke().onEach {
            _listMenu.postValue(it)
        }.launchIn(viewModelScope)
    }

    private fun getNews() {
        //Log.d("AAAAAAAA", "getNews")
        viewModelScope.launch {
            showLoading(true)
            newsRepositoryImpl.getNews(
                onSuccess = {
                    _listNews.postValue(it.articles)
                    insertArticles(it.articles)
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

    fun insertArticles(list: List<Articles>) {
        CoroutineScope(Dispatchers.IO).launch {
            articlesUseCase.deleteAllArticles
            if (list.isNullOrEmpty()) {
                return@launch
            }
            for ((index, articles) in list.withIndex()) {
                articles.id = index
                if (articles.author == null) {
                    articles.author = index.toString()
                }
                if (articles.urlToImage == null) {
                    articles.urlToImage = ""
                }
                if (articles.description == null) {
                    articles.description = ""
                }
                articlesUseCase.addArticles(articles)
            }
        }
    }

    fun getArticlePage(): LiveData<PagingData<Articles>> {
        val response = articlesUseCase.getArticlePage.invoke().cachedIn(viewModelScope)
        _listArticles.postValue(articlesUseCase.getArticlePage.invoke().cachedIn(viewModelScope).value)
        return response
    }

    fun setArticle(articles: Articles){
        _articles.value = articles
    }

    fun setOldFragment(tag: String){
        oldFragment = tag
    }

    fun getOldFragment() = oldFragment

}