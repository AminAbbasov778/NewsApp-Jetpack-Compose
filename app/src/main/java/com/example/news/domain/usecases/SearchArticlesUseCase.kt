package com.example.news.domain.usecases

import com.example.news.presentation.model.news.ArticleUi
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor() {
    operator fun invoke(news: List<ArticleUi>, query: String): List<ArticleUi> {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isBlank()) return news
        return news.filter {
            it.title.contains(trimmedQuery, ignoreCase = true) || it.description.contains(
                trimmedQuery,
                ignoreCase = true
            ) || it.author.contains(trimmedQuery, ignoreCase = true)
        }
    }
}