package com.example.reviewapp.data.local.db.dao

import androidx.room.*
import com.example.reviewapp.data.model.ArticlesEntity
import com.example.reviewapp.domain.model.Articles

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articlesEntity: ArticlesEntity)

    @Query("SELECT * FROM articles")
    suspend fun getAll(): List<Articles>

    @Query("DELETE  FROM articles")
    suspend fun deleteAll()

    @Delete
    fun delete(articlesEntity: ArticlesEntity)
}