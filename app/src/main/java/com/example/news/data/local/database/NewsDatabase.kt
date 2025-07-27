package com.example.news.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.data.local.daos.NewsDao
import com.example.news.data.local.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun newsDao(): NewsDao

}