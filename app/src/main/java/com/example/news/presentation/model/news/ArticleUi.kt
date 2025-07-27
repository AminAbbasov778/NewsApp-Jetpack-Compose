package com.example.news.presentation.model.news

import android.os.Parcelable
import com.example.news.domain.models.SourceModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ArticleUi(
    val author: String,
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: String,
    val url : String,
    val content: String,
    val source: SourceUi
) : Parcelable