package com.example.news.domain.interfaces

import com.example.news.data.local.entities.ArticleEntity
import com.example.news.domain.models.ArticleModel

interface NewsRepository {

    suspend fun getNews(keyword: String): Result<List<ArticleModel>>

    suspend fun saveNews(news: ArticleModel): Result<Unit>

    suspend fun getNewsFromLocal(): Result<List<ArticleModel>>

    suspend fun deleteNewsFromLocal(news : ArticleModel): Result<Unit>

    suspend fun isNewsSaved(url : String): Result<ArticleEntity?>
}