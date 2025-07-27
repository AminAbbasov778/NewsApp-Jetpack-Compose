package com.example.news.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class ArticleEntity(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded val source: SourceEntity,
    val title: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String,
)