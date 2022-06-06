package com.example.reviewapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reviewapp.data.base.EntityMapper
import com.example.reviewapp.data.base.ModelEntity
import com.example.reviewapp.domain.model.Articles
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.model.News
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "articles")
data class ArticlesEntity(
    @PrimaryKey(autoGenerate = true)
    @field: SerializedName("id") val id: Int,
    @field: SerializedName("author") val author: String,
    @field: SerializedName("title") val title: String,
    @field: SerializedName("description") val description: String,
    @field: SerializedName("url") val url: String,
    @field: SerializedName("urlToImage") val urlToImage: String,
    @field: SerializedName("content") val content: String,
):ModelEntity()

class ArticlesEntityMapper @Inject constructor() : EntityMapper<Articles, ArticlesEntity> {
    override fun mapToDomain(entity: ArticlesEntity): Articles = Articles(
        id = entity.id,
        author = entity.author,
        title = entity.title,
        description = entity.description,
        url = entity.url,
        urlToImage = entity.urlToImage,
        content = entity.content
    )

    override fun mapToEntity(model: Articles): ArticlesEntity = ArticlesEntity(
        id = model.id,
        author = model.author,
        title = model.title,
        description = model.description,
        url = model.url,
        urlToImage = model.urlToImage,
        content = model.content,
    )
}




