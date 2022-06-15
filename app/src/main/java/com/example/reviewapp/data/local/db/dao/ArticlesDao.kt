package com.example.reviewapp.data.local.db.dao

import androidx.room.*
import com.example.reviewapp.data.model.ArticlesEntity
import com.example.reviewapp.domain.model.Articles

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(articlesEntity: ArticlesEntity)

    @Query("SELECT * FROM articles")
    fun getAll(): List<Articles>

    @Query("SELECT * FROM articles WHERE id BETWEEN ((:page - 1)*:pageSize) AND (:page * :pageSize)")
    suspend fun getPage(page: Int, pageSize: Int): List<Articles>

    @Query("DELETE  FROM articles")
    fun deleteAll()

    @Delete
    fun delete(articlesEntity: ArticlesEntity)
}