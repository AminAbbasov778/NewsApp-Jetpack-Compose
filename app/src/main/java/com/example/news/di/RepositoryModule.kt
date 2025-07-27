package com.example.news.di

import android.content.SharedPreferences
import com.example.news.data.local.database.NewsDatabase
import com.example.news.data.remote.RequestService
import com.example.news.data.repository.LanguageRepositoryImpl
import com.example.news.data.repository.NewsRepositoryImpl
import com.example.news.domain.interfaces.LanguageRepository
import com.example.news.domain.interfaces.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(requestService: RequestService,database: NewsDatabase): NewsRepository {
        return NewsRepositoryImpl(requestService,database)

    }

    @Provides
    @Singleton
    fun provideLanguageRepository(sharedPreferences: SharedPreferences): LanguageRepository =
        LanguageRepositoryImpl(sharedPreferences)

}