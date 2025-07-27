package com.example.news.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.local.entities.ArticleEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(news: ArticleEntity)

    @Query("SELECT * FROM news")
    suspend fun getNews(): List<ArticleEntity>

    @Delete
   suspend fun deleteNews(news: ArticleEntity)

    @Query("SELECT * FROM news WHERE url = :url LIMIT 1")
    suspend fun getArticleByUrl(url: String): ArticleEntity?

}