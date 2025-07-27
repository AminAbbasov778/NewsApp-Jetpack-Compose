package com.example.news.domain.usecases

import com.example.news.domain.interfaces.NewsRepository
import com.example.news.domain.models.ArticleModel
import javax.inject.Inject

class DeleteNewsFromLocalUseCase @Inject constructor(val repository: NewsRepository) {
    suspend operator fun invoke(news: ArticleModel) = repository.deleteNewsFromLocal(news)
}