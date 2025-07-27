package com.example.news.data.repository


import com.example.news.data.local.database.NewsDatabase
import com.example.news.data.local.entities.ArticleEntity
import com.example.news.data.mappers.toData
import com.example.news.data.mappers.toDomain
import com.example.news.data.remote.RequestService
import com.example.news.domain.interfaces.NewsRepository
import com.example.news.domain.models.ArticleModel
import jakarta.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val requestService: RequestService,
    val database: NewsDatabase,
) : NewsRepository {
    override suspend fun getNews(keyword: String): Result<List<ArticleModel>> {
        return try {

            val response = requestService.getNews(keyword)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.articles.map { it.toDomain() })
                } ?: Result.failure(Exception())

            } else {
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)

        }
    }

    override suspend fun saveNews(news: ArticleModel): Result<Unit> {
        return try {
            database.newsDao().saveNews(news.toData())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getNewsFromLocal(): Result<List<ArticleModel>> {
        return try {
            val newsList = database.newsDao().getNews()

            Result.success(newsList.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNewsFromLocal(news : ArticleModel): Result<Unit> {
        return try {
            database.newsDao().deleteNews(news.toData())
            Result.success(Unit)
        }
        catch (e: Exception){
            Result.failure(e)

        }
    }

    override suspend fun isNewsSaved(url : String): Result<ArticleEntity?> {
     return   try {
          val isNewsSaved =  database.newsDao().getArticleByUrl(url)
         Result.success(isNewsSaved)
     }catch (e: Exception){
            Result.failure(e)
        }
    }


}