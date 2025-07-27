package com.example.news.domain.models

import com.example.news.data.models.Source
import com.google.gson.annotations.SerializedName

class ArticleModel(
                      val author: String,
                      val content: String,
                      val description: String,
                      val publishedAt: String,
                      val source: SourceModel,
                      val title: String,
                      val url: String,
                      val urlToImage: String) {
}