package com.example.news.domain.usecases

import com.example.news.domain.interfaces.NewsRepository
import com.example.news.domain.models.ArticleModel
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(keyword: String) = newsRepository.getNews(keyword)


}