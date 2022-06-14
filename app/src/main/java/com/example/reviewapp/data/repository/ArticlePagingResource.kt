package com.example.reviewapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reviewapp.data.local.db.dao.ArticlesDao
import com.example.reviewapp.domain.model.Articles
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 6

class ArticlePagingResource(private val articlesDao: ArticlesDao): PagingSource<Int,Articles>() {

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            delay(10000)
            val notes = articlesDao.getPage(position, params.loadSize)
            LoadResult.Page(
                data = notes,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (notes.isEmpty()) null else position + (params.loadSize / PAGE_SIZE)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}