package com.example.news.data.mappers

import android.R.attr.author
import android.R.attr.content
import android.R.attr.description
import com.example.news.data.local.entities.ArticleEntity
import com.example.news.data.local.entities.SourceEntity
import com.example.news.data.models.Article
import com.example.news.data.models.News
import com.example.news.domain.models.ArticleModel
import com.example.news.domain.models.SourceModel



fun Article.toDomain(): ArticleModel{

   return ArticleModel( author ?: "empty", content ?: "empty", description ?: "empty", publishedAt ?: "empty",
      SourceModel(source.id ?: "empty", source.name ?: "empty") ,title ?: "empty", url ?: "empty", urlToImage ?: "empty")


}

fun ArticleModel.toData(): ArticleEntity{

   return ArticleEntity( author , content , description , publishedAt ,
      SourceEntity(source.id, source.name ) ,title , url , urlToImage )

}

fun ArticleEntity.toDomain(): ArticleModel{

   return ArticleModel( author , content , description , publishedAt ,
      SourceModel(source.id, source.name ) ,title , url , urlToImage )

}
