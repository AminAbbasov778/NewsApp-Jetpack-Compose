package com.example.news.domain.usecases

import com.example.news.domain.interfaces.NewsRepository
import com.example.news.domain.models.ArticleModel
import javax.inject.Inject

class SaveNewsToLocalUseCase @Inject constructor(val newsRepository: NewsRepository) {
    suspend operator fun invoke(news: ArticleModel) = newsRepository.saveNews(news)
}