package com.example.news.domain.interfaces

interface LanguageRepository {
    fun saveLanguage(code : String)
    fun getLanguage() : String
}