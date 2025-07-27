package com.example.news.presentation.mappers

import com.example.news.domain.models.ArticleModel
import com.example.news.domain.models.SourceModel
import com.example.news.presentation.model.news.ArticleUi
import com.example.news.presentation.model.news.SourceUi

fun ArticleModel.toUi(): ArticleUi {
    return ArticleUi(
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt.take(10),
        content = content,
        source = SourceUi(name = source.name,id = source.id)
    )
}

fun ArticleUi.toDomain(): ArticleModel {
    return ArticleModel(
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        url = url,
        publishedAt = publishedAt.take(10),
        content = content,
        source = SourceModel(name = source.name,id= source.id)
    )
}