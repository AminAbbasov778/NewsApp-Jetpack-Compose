package com.example.news.presentation.model

import com.example.news.presentation.model.news.ArticleUi
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(
    val article: ArticleUi,

)